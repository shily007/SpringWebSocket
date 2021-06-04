package com.dy.sws.service.impl;

import com.dy.sws.entity.User;
import com.dy.sws.mapper.UserMapper;
import com.dy.sws.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dy
 * @since 2021-06-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
