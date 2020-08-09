package com.example.projectdemo.common.enums;

/**
 * 成果水平
 */
public enum ProductionLevel implements EnumTypeInterface {

    /**
     * 国际领先
     */
    INTERNATIONAL_LEADING(1, "国际领先"),

    /**
     * 国际先进
     */
    INTERNATIONAL_ADVANCED(2, "国际先进"),

    /**
     * 国内领先
     */
    DOMESTIC_LEADING(3, "国内领先"),

    /**
     * 国内先进
     */
    DOMESTIC_ADVANCED(4, "国内先进"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    ProductionLevel(int value, String desc) {
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

    public static ProductionLevel get(int value) {
        for (ProductionLevel status : ProductionLevel.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
