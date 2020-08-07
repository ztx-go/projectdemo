package com.example.projectdemo.common.enums;

/**
 * 评价方式
 */
public enum EvaluationWay implements EnumTypeInterface {

    /**
     * 鉴定
     */
    AUTHENTICATE_WAY(1, "鉴定"),

    /**
     * 验收
     */
    ACCEPT_WAY(2, "验收"),

    /**
     * 评审
     */
    REVIEW_WAY(3, "评审"),

    /**
     * 结题
     */
    CONCLUSION_WAY(4, "结题"),

    /**
     * 其他
     */
    OTHER(0, "");

    private int value;
    private String desc;

    EvaluationWay(int value, String desc) {
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

    public static EvaluationWay get(int value) {
        for (EvaluationWay status : EvaluationWay.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
