/*
 * @(#)MapTree.java
 *
 * Copyright 2013 Vanda, Inc. All rights reserved.
 */

package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.MapTreeType;
import com.example.projectdemo.common.enums.UseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * 码表树对象
 * 
 * @author hc
 * @version 1.0,2013-4-15
 */
@ApiModel(value = "MapTreeEntity")
@TypeDefs({
    @TypeDef(name = "maptreeType", typeClass = EnumType.class, parameters = {
        @Parameter(name = "class", value = "com.example.projectdemo.common.enums.MapTreeType")}),
    @TypeDef(name = "stateType", typeClass = EnumType.class, parameters = {
        @Parameter(name = "class", value = "com.example.projectdemo.common.enums.UseStatus")})})
@Entity
@Table(name = "map_tree")
public class MapTreeEntity extends UuidEntity {


  /**
   * 
   */
  private static final long serialVersionUID = 2216364939747541311L;
  /**
   * 基础位置 码表树根节点
   */
  public static final String ROOT = "0";
  /**
   * 部门机构
   */
  public static final String INSTITUTIONAL = "1";
  
  /*** 名称 */
  @Column(name = "name", nullable = false)
  private String name;
  /*** 码表全名 */
  @Transient
  private String mapTreeName;
  /** * 介绍 */
  @Column(name = "introduction")
  private String introduction;
  /*** 父节点 */
  @ManyToOne
  @JoinColumn(name = "parent_id")
  @ApiModelProperty(hidden = true)
  private MapTreeEntity parent;
  /*** 显示层级关系 */
  @Column(name = "code")
  private String code;
  /*** 码表类型 */
  @Type(type = "maptreeType")
  @Column(name = "type", nullable = false)
  private MapTreeType maptreeType;
  /** 用于描述码表类型 不用存储到数据库 */
  @Transient
  private String typeDesc;
  /*** 码表树子类* */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
  @OrderBy("id")
  @ApiModelProperty(hidden = true)
  private List<MapTreeEntity> children;
  /*** 顺序 */
  @Column(name = "item_index")
  public int index;
  /*** 是否做逻辑删除 * true 为删除 */
  @Type(type = "boolean")
  @Column(name = "is_delete")
  private boolean del = false;
  /** * 时间戳，用于获取服务器端的数据,当服务端时间大于这个时间戳的都会被获取 */
  @Type(type = "timestamp")
  @Column(name = "ts", columnDefinition = "timestamp")
  private Timestamp ts;
  /** * 状态 */
  @Type(type = "stateType")
  @Column(name = "state_type", nullable = false)
  private UseStatus stateType = UseStatus.STATUS_NORMAL;
  /** 子节点数量 */
  @Column(name = "child_count")
  private int childCount = 0;
  /** 创建人. **/
  @ApiModelProperty(hidden = true)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id")
  private UserEntity creator;
  /** 修改人. **/
  @ApiModelProperty(hidden = true)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modifier_id")
  private UserEntity modifier;
  /** 创建时间. **/
  @Column(name = "create_date")
  private Date createDate = new Date();
  /** 修改时间. **/
  @Column(name = "modify_date")
  private Date modifyDate;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public MapTreeEntity getParent() {
    return parent;
  }

  public void setParent(MapTreeEntity parent) {
    this.parent = parent;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public MapTreeType getMaptreeType() {
    return maptreeType;
  }

  public void setMaptreeType(MapTreeType maptreeType) {
    this.maptreeType = maptreeType;
  }

  public List<MapTreeEntity> getChildren() {
    return children;
  }

  public void setChildren(List<MapTreeEntity> children) {
    this.children = children;
  }



  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * @return the del
   */
  public boolean isDel() {
    return del;
  }

  /**
   * @param del the del to set
   */
  public void setDel(boolean del) {
    this.del = del;
  }

  public Timestamp getTs() {
    return ts;
  }

  public void setTs(Timestamp ts) {
    this.ts = ts;
  }

  public UseStatus getStateType() {
    return stateType;
  }

  public void setStateType(UseStatus stateType) {
    this.stateType = stateType;
  }

  public int getChildCount() {
    return childCount;
  }

  public void setChildCount(int childCount) {
    this.childCount = childCount;
  }

  public UserEntity getCreator() {
    return creator;
  }

  public void setCreator(UserEntity creator) {
    this.creator = creator;
  }

  public UserEntity getModifier() {
    return modifier;
  }

  public void setModifier(UserEntity modifier) {
    this.modifier = modifier;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  // public String getTypeDesc() {
  //   return maptreeType == null ? null : maptreeType.getDesc();
  // }
  //
  // public void setTypeDesc(String typeDesc) {
  //   this.typeDesc = maptreeType == null ? null : maptreeType.getDesc();
  // }

  public String getMapTreeName() {
    return mapTreeName;
  }

  public void setMapTreeName(String mapTreeName) {
    this.mapTreeName = mapTreeName;
  }

  /**
   * 判断是否是叶子节点
   * 
   * @return
   */
  @Transient
  public boolean isLeaf() {
    return children == null || children.isEmpty() || isDel(children) ? true : false;
  }

  /**
   * 判断其子类是否有被逻辑删除
   * 
   * @return true 没有子类要显示 false 有子类要显示
   */
  @Transient
  public boolean isDel(List<MapTreeEntity> map) {
    if (map == null || map.isEmpty()) {
      return true;
    } else {
      Iterator<MapTreeEntity> it = map.iterator();
      while (it.hasNext()) {
        if (!it.next().isDel()) {
          return false;
        }
      }
    }
    return true;
  }

}
