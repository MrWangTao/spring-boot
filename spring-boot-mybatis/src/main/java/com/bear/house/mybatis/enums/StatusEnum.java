package com.bear.house.mybatis.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangTao
 *         Created at 18/7/10 上午11:16.
 */
public enum StatusEnum {

    ENABLE(1, "启用"),
    DISABLE(2, "禁用");

    @Getter
    @Setter
    private int value;
    @Getter
    @Setter
    private String displayName;

    StatusEnum(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    // 便于进行value转换到枚举类型
    static Map<Integer, StatusEnum> enumMap = new HashMap<Integer, StatusEnum>();

    static {
        for (StatusEnum type : StatusEnum.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    public static StatusEnum getEnum(int value) {
        return enumMap.get(value);
    }

}
