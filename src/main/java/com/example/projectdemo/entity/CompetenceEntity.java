/**
 * 权限.
 */
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
 * @author yinwenjie
 * @version V1.0
 */
@ApiModel(value = "CompetenceEntity")
@Entity
@Table(name = "eb_competence")
@TypeDefs({ @TypeDef(name = "useStatus", typeClass = EnumType.class, parameters = {
		@Parameter(name = "class", value = "com.example.projectdemo.enums.UseStatus") }) })
public class CompetenceEntity extends UuidEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7742962048681654604L;

	/** 权限URL串. **/
	@Column(name = "resource", nullable = false)
	private String resource = "";

	/**
	 * 涉及的方法描述<br>
	 * 例如：POST或者POST|GET|DELETE
	 */
	@Column(name = "methods", nullable = false)
	private String methods = "";

	/** 创建时间. **/
	@Column(name = "create_date", nullable = false)
	private Date createDate = new Date();

	/** 修改时间. **/
	@Column(name = "modify_date")
	private Date modifyDate;

	/** 状态 1正常, 0禁用（枚举）. **/
	@Type(type = "useStatus")
	@Column(name = "status", nullable = false)
	private UseStatus status = UseStatus.STATUS_NORMAL;

	/**
	 * 功能对应的角色信息
	 */
	@ApiModelProperty(hidden = true)
	@ManyToMany(mappedBy = "competences")
	private Set<RoleEntity> roles;

	/** 备注. **/
	@Column(name = "comment", nullable = false)
	private String comment = "";
	
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

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
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
