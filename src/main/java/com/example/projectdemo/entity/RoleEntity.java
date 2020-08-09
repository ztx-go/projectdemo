/**
 * 角色.
 */
package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.EnumType;
import com.example.projectdemo.common.enums.UseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * 角色
 */
@ApiModel(value = "RoleEntity")
@Entity
@Table(name = "eb_role")
@TypeDefs({@TypeDef(name = "useStatus", typeClass = EnumType.class, parameters = {
    @Parameter(name = "class", value = "com.example.projectdemo.common.enums.UseStatus")})})
public class RoleEntity extends UuidEntity {

  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = -4750396018968101826L;

  /** 角色名称. **/
  @Column(name = "name", length = 64, nullable = false, unique = true)
  private String name = "";

  /** 创建时间. **/
  @Column(name = "create_date", nullable = false)
  private Date createDate = new Date();

  /** 修改时间. **/
  @Column(name = "modify_date")
  private Date modifyDate;

  /** 状态 1正常, 0禁用(枚举). **/
  @Type(type = "useStatus")
  @Column(name = "status", nullable = false)
  private UseStatus status = UseStatus.STATUS_NORMAL;

  /** 备注.角色信息说明 **/
  @Column(name = "comment", length = 64, nullable = true)
  private String comment;

  /** 功能. **/
  @ApiModelProperty(hidden = true)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_competence", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "competence_id")})
  private Set<CompetenceEntity> competences;

  /** ladp用户节点. **/
  @ApiModelProperty(hidden = true)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_user", joinColumns = {
      @JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<UserEntity> users;

  /**创建人*/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "create_user")
  @ApiModelProperty(hidden = true)
  private UserEntity createUser;

  /** 修改人 **/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modify_user")
  @ApiModelProperty(hidden = true)
  private UserEntity modifyUser;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public UseStatus getStatus() {
    return status;
  }

  public void setStatus(UseStatus status) {
    this.status = status;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Set<CompetenceEntity> getCompetences() {
    return competences;
  }

  public void setCompetences(Set<CompetenceEntity> competences) {
    this.competences = competences;
  }

  public Set<UserEntity> getUsers() {
    return users;
  }

  public void setUsers(Set<UserEntity> users) {
    this.users = users;
  }

  public UserEntity getCreateUser() {
    return createUser;
  }

  public UserEntity getModifyUser() {
    return modifyUser;
  }

  public void setCreateUser(UserEntity createUser) {
    this.createUser = createUser;
  }

  public void setModifyUser(UserEntity modifyUser) {
    this.modifyUser = modifyUser;
  }
  
}
