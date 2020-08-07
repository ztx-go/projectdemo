package com.example.projectdemo.common.enums;

/**
 * 所处阶段
 */
public enum ProductionStage implements EnumTypeInterface {

    /**
     * 初期
     */
    PRELIMINARY_STAGE(1,"初期"),

    /**
     * 中期
     */
    INTERIM_STAGE(2,"中期"),

    /**
     * 成熟应用
     */
    MATURE_STAGE(3,"成熟应用"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    ProductionStage(int value, String desc) {
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

    public static ProductionStage get(int value) {
        for (ProductionStage status : ProductionStage.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
