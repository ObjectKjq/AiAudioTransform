package com.kjq.model.vo.user;

import com.baomidou.mybatisplus.annotation.*;
import com.kjq.model.vo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
public class UserPageReqVO extends PageParam {

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色（0用户1管理员）
     */
    private Integer role;

    /**
     * 是禁用（0否1是）
     */
    private Integer isDisable;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime[] createTime;

}
