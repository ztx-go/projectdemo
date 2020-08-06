package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.UseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 系统用户基本信息.
 * 
 * @author hzh
 * @version V 0.0.9
 */
@ApiModel(value = "Operator")
@Entity
@Table(name = "e_operator")
@TypeDefs({@TypeDef(name = "useStatus", typeClass = EnumType.class, parameters = {
    @Parameter(name = "class", value = "com.antiDrugPropaganda.common.enums.UseStatus")})})
public class OperatorEntity extends UuidEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -8601837102945262094L;

  /** 账号. **/
  @Column(name = "account", unique = true, nullable = false)
  private String account = "";

  /** 密码. **/
  @Column(name = "password")
  private String password = "";

  /** 姓名. **/
  @Column(name = "name", nullable = false)
  private String name = "";

  /** 状态 1正常, 2禁用(枚举). **/
  @Type(type = "useStatus")
  @Column(name = "status", nullable = false)
  protected UseStatus status = UseStatus.STATUS_NORMAL;

  /** 头像. **/
  @Column(name = "head")
  private String head;
  
  /*** 所属地区 */
  @ManyToOne
  @JoinColumn(name = "area_")
  @ApiModelProperty(hidden = true)
  private MapTreeEntity area;
  

  /**
   * 是否可以审核
   */
  @Column(name = "has_review")
  private Boolean hasReview = false;

  /**
   * 上次登录时间
   */
  @Column(name = "last_login_date")
  private Date lastLoginDate;

  /** 创建人 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "create_user")
  @ApiModelProperty(hidden = true)
  private OperatorEntity createUser;

  /** 修改人 **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modify_user")
  @ApiModelProperty(hidden = true)
  private OperatorEntity modifyUser;

  /** 创建时间. **/
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();

  /** 修改时间. **/
  @Column(name = "modify_date")
  private Date modifyDate;

  /** 角色——和管理端用户的相关. **/
  @ApiModelProperty(hidden = true)
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "operators")
  private Set<RoleEntity> roles;



  public Set<RoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleEntity> roles) {
    this.roles = roles;
  }

  public OperatorEntity getCreateUser() {
    return createUser;
  }

  public void setCreateUser(OperatorEntity createUser) {
    this.createUser = createUser;
  }

  public OperatorEntity getModifyUser() {
    return modifyUser;
  }

  public void setModifyUser(OperatorEntity modifyUser) {
    this.modifyUser = modifyUser;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UseStatus getStatus() {
    return status;
  }

  public void setStatus(UseStatus status) {
    this.status = status;
  }

  public String getHead() {
    return head;
  }

  public void setHead(String head) {
    this.head = head;
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

  public Date getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(Date lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  public MapTreeEntity getArea() {
    return area;
  }

  public void setArea(MapTreeEntity area) {
    this.area = area;
  }

  public Boolean getHasReview() {
    return hasReview;
  }

  public void setHasReview(Boolean hasReview) {
    this.hasReview = hasReview;
  }

}
