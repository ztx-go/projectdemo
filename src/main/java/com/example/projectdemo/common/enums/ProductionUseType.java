package com.example.projectdemo.common.enums;

/**
 * 应用状态
 */
public enum ProductionUseType implements EnumTypeInterface {

    /**
     * 稳定应用
     */
    STABLE_USE(1, "稳定应用"),

    /**
     * 应用后停用
     */
    AFTER_STOP_USE(2, "应用后停用"),

    /**
     * 未使用
     */
    UN_USE(3, "未使用"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    ProductionUseType(int value, String desc) {
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

    public static ProductionUseType get(int value) {
        for (ProductionUseType status : ProductionUseType.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
