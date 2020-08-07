package com.example.projectdemo.common.enums;

/**
 * 
 * 操作日志实体对象类型
 *
 *
 */
public enum EntityType implements EnumTypeInterface {
  /** 企业信息 */
  ENTERPRISESENTITY(3, "企业信息"),
  /** 楼层信息 */
  FLOORENTITY(4, " 楼层信息"),
  /** 楼宇信息 */
  BUILDINGENTITY(5, "楼宇信息"),
  /**用户对象*/
  USERENTITY(6,"用户对象"),
  /**其他*/
  OTHER(10,"其他")
 ;
  private int value;
  private String desc;

  EntityType(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public int getValue() {
    return this.value;
  }

  public String getDesc() {
    return this.desc;
  }

  public static EntityType get(int value) {
    for (EntityType status : EntityType.values()) {
      if (status.value == value) {
        return status;
      }
    }
    throw new IllegalArgumentException("argument error: " + value);
  }

}
