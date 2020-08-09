package com.example.projectdemo.common.enums;

/**
 * 企业属性
 */
public enum EnterpriseProperty implements EnumTypeInterface {

    /**
     * 盈利企业
     */
    PROFIT_ENTERPRISE(1,"盈利企业"),

    /**
     * 公益企业
     */
    COMMONWEAL_ENTERPRISE(2,"公益企业"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    EnterpriseProperty(int value, String desc) {
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

    public static EnterpriseProperty get(int value) {
        for (EnterpriseProperty status : EnterpriseProperty.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
