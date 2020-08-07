package com.example.projectdemo.common.enums;

/**
 * StreetType
 */
public enum StreetType implements EnumTypeInterface {

  
  /**
   * 桂溪街道
   */
  GUIXI_STREET(1, "桂溪"),
  
  /**
   * 石羊场街道
   */
  SHIYANGCHANG_STREET(2,"石羊"),
  
  /**
   * 芳草街道
   */
  FANGCAO_STREET(3,"芳草"),
  
  /**
   * 肖家河街道
   */
  XIAOJIAHE_STREET(4,"肖家河"),
  
  /**
   * 中和街道
   */
  ZHONGHE_STREET(7,"中和"),
  
  /**
   * 合作街道
   */
  HEZUO_STREET(5,"合作"),
  
  /**
   * 西园街道
   */
  XIYUAN_STREET(6,"西园"),
  
  ;

  private int value;
  private String desc;

  StreetType(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public int getValue() {
    return this.value;
  }

  public String getDesc() {
    return this.desc;
  }


  public static StreetType get(int value) {
    for (StreetType status : StreetType.values()) {
      if (status.value == value) {
        return status;
      }
    }
    throw new IllegalArgumentException("argument error: " + value);
  }
}
