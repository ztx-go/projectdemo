package com.example.projectdemo.common.enums;

/**
 * 作品类型
 */
public enum ProductionType implements EnumTypeInterface  {

    /**
     * 文字作品
     */
    LITERAL_TYPE(1, "文字作品"),

    /**
     * 口述作品
     */
    DICTATE_TYPE(2, "口述作品"),

    /**
     * 音乐、戏剧杂技、舞蹈、曲艺艺术作品
     */
    MUSIC_TYPE(3, "音乐、戏剧杂技、舞蹈、曲艺艺术作品"),

    /**
     * 美术、建筑作品,摄影作品
     */
    ART_TYPE(4, "美术、建筑作品,摄影作品"),

    /**
     * 摄影作品
     */
    PHOTOGRAPHY_TYPE(5, "摄影作品"),

    /**
     * 电影作品和以类似手法创作的作品
     */
    MOVIE_TYPE(6, "电影作品和以类似手法创作的作品"),

    /**
     * 图形、模型
     */
    GRAPH_TYPE(7, "图形、模型"),

    /**
     * 其他
     */
    OTHER(0, "其他");

    private int value;
    private String desc;

    ProductionType(int value, String desc) {
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

    public static ProductionType get(int value) {
        for (ProductionType status : ProductionType.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("argument error: " + value);
    }
}
