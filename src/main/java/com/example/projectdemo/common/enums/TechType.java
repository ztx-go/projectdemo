package com.example.projectdemo.common.enums;

/**
 * 技术类型
 */
public enum TechType implements EnumTypeInterface {

    /**
     * 基础类型
     */
    BASIC_TYPE(1, "基础类型"),

    /**
     * 理论
     */
    THEORY_TYPE(2, "理论"),

    /**
     * 软科学
     */
    SOFT_SCIENCE_TYPE(3, "软科学"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    TechType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public static TechType get(int value) {
        for (TechType status : TechType.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
