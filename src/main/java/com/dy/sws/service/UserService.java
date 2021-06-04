package com.dy.sws.service;

import com.dy.sws.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dy
 * @since 2021-06-02
 */
public interface UserService extends IService<User> {

	/**
	 * 根据用户名查询用户
	 * getByUsername
	 * @param username
	 * @return
	 * @author dy
	 * 2021年6月3日
	 */
	default User getByUsername(String username) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		return getOne(queryWrapper);
	}

}
