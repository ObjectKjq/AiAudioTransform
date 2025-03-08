package com.kjq.model.vo.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserBaseVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色（0用户1管理员）
     */
    private Integer role;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 是禁用（0否1是）
     */
    private Integer isDisable;

}
