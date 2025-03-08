package com.kjq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 全局用户类型枚举
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum {

    USER(0, "用户"),
    ADMIN(1, "管理员");

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

}
