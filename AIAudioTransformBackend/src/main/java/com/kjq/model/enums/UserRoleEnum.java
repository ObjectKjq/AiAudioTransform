package com.kjq.model.enums;

import com.kjq.constant.UserConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 全局用户类型枚举
 */
@AllArgsConstructor
@Getter
public enum UserRoleEnum {

    USER(0, "用户", UserConstant.DEFAULT_ROLE),
    ADMIN(1, "管理员", UserConstant.ADMIN_ROLE);

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;
    /**
     * 类型
     */
    private final String type;

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
