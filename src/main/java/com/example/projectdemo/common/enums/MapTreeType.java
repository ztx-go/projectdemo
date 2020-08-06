/*
 * @(#)MapTreeType.java
 *
 * Copyright 2016 Vanda, Inc. All rights reserved.
 */
package com.example.projectdemo.common.enums;


/**
 * 码表类型
 * 
 * @author fwguang
 *
 */
public enum MapTreeType implements EnumTypeInterface {

  /** 根 */
  MAP_TREE_TYPE_ROOT(0, "码表树"),
  /** 高新区*/
  INSTITUTIONAL(1,"部门机构"),
  /**其他*/
  MAP_TREE_TYPE_OTHER(20,"其他");
  

  private int value;
  private String desc;

  MapTreeType(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  @Override
  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public static MapTreeType get(int value) {
    for (MapTreeType status : MapTreeType.values()) {
      if (status.value == value) {
        return status;
      }
    }
    throw new IllegalArgumentException("argument error: " + value);
  }
}
