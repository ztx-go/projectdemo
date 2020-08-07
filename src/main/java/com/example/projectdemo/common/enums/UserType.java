package com.example.projectdemo.common.enums;

/**
 * 用户类型.
 */
public enum UserType implements EnumTypeInterface {
  
  /**
   * 部门人员
   */
  DEPART_STAFF(1, "部门人员"),
  /**
   * 街道人员
   */
  STREET_STAFF(2, "街道人员"),
  /**
   * 楼宇人员
   */
  BUILD_STAFF(3, "楼宇人员"),
  
  /**
   * 其他
   */
  OTHER(0, "其他"),

  ;

  private int value;
  private String desc;

  UserType(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }


  public int getValue() {
    return this.value;
  }

  public String getDesc() {
    return this.desc;
  }

  public static UserType get(int value) {
    for (UserType status : UserType.values()) {
      if (status.value == value) {
        return status;
      }
    }
    throw new IllegalArgumentException("argument error: " + value);
  }
}
