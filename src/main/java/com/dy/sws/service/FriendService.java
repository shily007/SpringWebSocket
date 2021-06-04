package com.dy.sws.service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.sws.entity.Friend;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dy
 * @since 2021-06-02
 */
public interface FriendService extends IService<Friend> {
	
	/**
	 * 根据用户id获取朋友Id集合
	 * getFriendIdsByUserId
	 * @param userId
	 * @return
	 * @author dy
	 * 2021年6月2日
	 */
	default List<Long> getFriendIdsByUserId(Long userId){
		QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userId", userId);
		List<Friend> list = list(queryWrapper);
		List<Long> frendIds = new ArrayList<>();
		for (Friend friend : list) {
			frendIds.add(friend.getFriendId());
		}
		return frendIds;
	}

}
