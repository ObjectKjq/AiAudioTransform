package com.kjq.model.vo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


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
public class UserUpdateReqVO extends UserBaseVO {

    /**
     * ID
     */
    private Integer id;
    /**
     * 密码
     */
    private String password;

}
