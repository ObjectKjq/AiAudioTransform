package com.kjq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局用户类型枚举
 */
@AllArgsConstructor
@Getter
public enum IsDisableEnum {

    NO(0, "否"),
    YES(1, "是");

    /**
     * 类型
     */
    private final Integer status;
    /**
     * 类型名
     */
    private final String name;

}
