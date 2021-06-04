package com.dy.sws.server;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dy.sws.entity.User;
import com.dy.sws.service.FriendService;
import com.dy.sws.service.UserService;
import com.dy.sws.utils.JsonMsg;

@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {

	@Autowired
	private UserService userService;
	@Autowired
	private FriendService friendService;
	private static WebSocketServer webSocketServer;
	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	private static final AtomicInteger OnlineCount = new AtomicInteger(0);
	// concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
	private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();
	private static Map<String, User> userMap = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		webSocketServer = this;
		webSocketServer.userService = this.userService;
		webSocketServer.friendService = this.friendService;
	}

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session) {
		int cnt = OnlineCount.incrementAndGet(); // 在线数加1
		log.info("有连接加入，当前连接数为：{}", cnt);
//		SendMessage(session, JSONObject.toJSONString(new JsonMsg(0, "连接成功")));
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(Session session) {
		int cnt = OnlineCount.decrementAndGet();
		log.info("有连接关闭，当前连接数为：{}", cnt);
		User user = userMap.get(session.getId());
		if (user != null) {
			JsonMsg jsonMsg = new JsonMsg(99, "offline");
			jsonMsg.setFrom(user.getUsername());			
			List<User> friends = getFriends(user.getId());
			for (User friend : friends) {
				Session session2 = sessionMap.get(friend.getUsername());
				if (session2 != null) {
					sendMessage(session2, new JsonMsg(99, user.getUsername(), friend.getUsername(), "offline"));
				}
			}
			sessionMap.remove(user.getUsername());
			userMap.remove(session.getId());
		}
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("来自客户端的登录消息：{}", message);
		JsonMsg msg = JSONObject.parseObject(message, JsonMsg.class);
		if (msg.getAction() == 0) {
			login(session, msg);
		} else if (msg.getAction() == 1) {
			getFriendsAndNotify(session, msg);
		} else if (msg.getAction() == 2) {
			sendMsg(session,msg);
		} else {

		}
	}

	/**
	 * 普通消息发送
	 * sendMsg
	 * @param session
	 * @param msg
	 * @author dy
	 * 2021年6月3日
	 */
	private void sendMsg(Session session, JsonMsg msg) {
		if(StringUtils.isNoneEmpty(msg.getGroupId())) {//群消息
			
		}else {//普通消息
			if(StringUtils.isNoneEmpty(msg.getTo())) {//群消息
				String[] arr = msg.getTo().split(",");
				for (String username : arr) {
					Session session2 = sessionMap.get(username);
					sendMessage(session2, msg);
				}
			}else {
				sendMessage(session, new JsonMsg(97, "接收人不能为空！"));
			}
		}
	}

	/**
	 * 用户登录 login
	 * 
	 * @param session
	 * @param msg
	 * @author dy 2021年6月3日
	 */
	private void login(Session session, JsonMsg msg) {
		if (StringUtils.isEmpty(msg.getFrom())) {
			sendMessage(session, new JsonMsg(3, "用户名不能为空！"));
			onClose(session);
		}
		User user = webSocketServer.userService.getByUsername(msg.getFrom());
		if (user == null) {
			sendMessage(session, new JsonMsg(3, "用户不存在！"));
			onClose(session);
		}
		user.setLastTime(LocalDateTime.now());
		webSocketServer.userService.updateById(user);
		sessionMap.put(msg.getFrom(), session);
		userMap.put(session.getId(), user);
		sendMessage(session, new JsonMsg(0, 1, user));
	}

	/**
	 * 获取好友列表 getFriends
	 * 
	 * @param userId
	 * @return
	 * @author dy 2021年6月3日
	 */
	private List<User> getFriends(Long userId) {
		List<Long> friendIds = webSocketServer.friendService.getFriendIdsByUserId(userId);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("id", friendIds);
		queryWrapper.orderByDesc("lastTime");
		return webSocketServer.userService.list(queryWrapper);
	}

	/**
	 * 获取好友并发送通知 getFriendsAndNotify
	 * 
	 * @param session
	 * @param msg
	 * @author dy 2021年6月2日
	 */
	private void getFriendsAndNotify(Session session, JsonMsg msg) {
		User user = userMap.get(session.getId());
		if (user != null) {
			List<User> friends = getFriends(user.getId());
			List<User> list = new ArrayList<>();
			List<User> list2 = new ArrayList<>();
			for (User friend : friends) {
				Session session2 = sessionMap.get(friend.getUsername());
				if (session2 != null) {
					sendMessage(session2, new JsonMsg(88, user.getUsername(), friend.getUsername(), "online"));
					friend.setIsLogin(true);
					list.add(friend);
				}else {
					list2.add(friend);
				}
			}
			list.addAll(list2);
			sendMessage(session, new JsonMsg(1, msg.getFrom(), 1, list));
		} else {
			sendMessage(session, new JsonMsg(98, "offline"));
		}
	}

	/**
	 * 出现错误
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
		error.printStackTrace();
	}

	/**
	 * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
	 * 
	 * @param session
	 * @param message
	 */
	public static void sendMessage(Session session, Object message) {
		try {
			session.getBasicRemote().sendText(JSONObject.toJSONString(message));
		} catch (IOException e) {
			log.error("发送消息出错：{}", e.getMessage());
			e.printStackTrace();
		}
	}

}