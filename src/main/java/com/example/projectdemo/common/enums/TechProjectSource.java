package com.example.projectdemo.common.enums;

/**
 * 技术课题来源
 */
public enum TechProjectSource implements EnumTypeInterface {

    /**
     * 863计划(国家高技术研究发展计划)
     */
    HIGH_TECHNOLOGY_PROGRAM(1, "863计划"),

    /**
     * 国家科技攻关计划
     */
    NATIONAL_KEY_PROGRAM(2, "国家科技攻关计划"),

    /**
     * 基础研究计划
     */
    BASIC_RESEARCH_PROGRAM(3, "基础研究计划"),

    /**
     * 研究开发能力建设
     */
    RESEARCH_DEVELOP_CONSTRUCT(4, "研究开发能力建设"),

    /**
     * 科技产业化环境建设计划
     */
    TECH_ENVIRONMENT_PROGRAM(5, "科技产业化环境建设计划"),

    /**
     * 部门计划
     */
    DEPART_PROGRAM(6, "部门计划"),

    /**
     * 地方计划
     */
    PLACE_PROGRAM(7, "地方计划"),

    /**
     * 部门基金
     */
    DEPART_FUND(8, "部门基金"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    TechProjectSource(int value, String desc) {
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

    public static TechProjectSource get(int value) {
        for (TechProjectSource status : TechProjectSource.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
