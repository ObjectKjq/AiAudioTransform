package com.kjq.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否删除
 */
@AllArgsConstructor
@Getter
public enum DeletedEnum {

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
