package com.dy.sws.utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端发送消息
 * 
 * @author dy
 * 2021年6月3日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JsonMsg {
	
	//前端发送：0：登录，1：获取好友列表，2：消息，
	//前端接收：0：用户信息，1：好友列表，2：消息，3：系统消息，88：上线消息，97：发送消息失败，98：未登录，99：离线消息
	private Integer action = 3;
	//发送者
	private String from;
	//群id（发送群消息）
	private String groupId;
	//接收者
	private String to;
	//消息类别： 0：文本，1：json对象，2：图片，3：文件
	private Integer type = 0;
	//消息内容
	private Object msg;
	//状态：0：未读，1：已读
	private Integer state = 0;
	//时间
	private LocalDateTime time = LocalDateTime.now();
	
	public JsonMsg(Integer action, String to, Integer type, Object msg) {
		super();
		this.action = action;
		this.to = to;
		this.type = type;
		this.msg = msg;
	}

	public JsonMsg(String from, String to, Object msg) {
		super();
		this.from = from;
		this.to = to;
		this.msg = msg;
	}

	public JsonMsg(String to, Object msg) {
		super();
		this.to = to;
		this.msg = msg;
	}

	public JsonMsg(Integer action, Object msg) {
		super();
		this.action = action;
		this.msg = msg;
	}

	public JsonMsg(Integer action, String from, String to, Object msg) {
		super();
		this.action = action;
		this.from = from;
		this.to = to;
		this.msg = msg;
	}

	public JsonMsg(Integer action, Integer type, Object msg) {
		super();
		this.action = action;
		this.type = type;
		this.msg = msg;
	}
}
