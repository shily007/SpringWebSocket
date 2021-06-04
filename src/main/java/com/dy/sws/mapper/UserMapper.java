package com.dy.sws.mapper;

import com.dy.sws.entity.User;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dy
 * @since 2021-06-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
