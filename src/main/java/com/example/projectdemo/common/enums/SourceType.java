package com.example.projectdemo.common.enums;

public enum SourceType implements EnumTypeInterface {

    /**
     * 地域
     */
    TERRITORY_TYPE(1, "地域"),

    /**
     * 人群
     */
    CROWD_TYPE(2, "人群"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    SourceType(int value, String desc) {
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

    public static SourceType get(int value) {
        for (SourceType status : SourceType.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
