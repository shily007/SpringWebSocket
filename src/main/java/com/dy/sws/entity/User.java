package com.dy.sws.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author dy
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headimgurl;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 是否登录
     */
    @TableField(exist = false)
    private Boolean isLogin;

    /**
     * 最近登录时间
     */
    @TableField("lastTime")
    private LocalDateTime lastTime;

    /**
     * 注册时间
     */
    private LocalDateTime time;


}
