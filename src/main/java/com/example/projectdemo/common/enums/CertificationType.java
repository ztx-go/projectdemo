package com.example.projectdemo.common.enums;

/**
 * 实名认证
 */
public enum CertificationType implements EnumTypeInterface {

    /**
     * 个人认证
     */
    PERSONAL_CERTIFICATION(1,"个人认证"),

    /**
     * 企业认证
     */
    ENTERPRISE_CERTIFICATION(2,"企业认证"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    CertificationType(int value, String desc) {
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

    public static CertificationType get(int value) {
        for (CertificationType status : CertificationType.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
