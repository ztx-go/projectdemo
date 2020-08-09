package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.EnumType;
import com.example.projectdemo.common.enums.UseStatus;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "eb_user")
@TypeDefs({
        @TypeDef(name = "useStatus", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.UseStatus")})})
public class UserEntity extends UuidEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2530517105565916389L;

    /**
     * 用户账户
     */
    @Column(name = "user_account", unique = true, nullable = false)
    private String account;

    /**
     * 密码.
     **/
    @Column(name = "password")
    private String password = "";

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    /**
     * 用户名称
     */
    @Column(name = "user_name", nullable = false)
    private String userName;

    /**
     * 状态 1正常, 0禁用(枚举).
     **/
    @Type(type = "useStatus")
    @Column(name = "status", nullable = false)
    private UseStatus status = UseStatus.STATUS_NORMAL;

    /**
     * 是否重置密码.
     **/
    @Column(name = "is_reset_psd")
    private Boolean isResetPsd = false;

    /**
     * 手机号码.
     **/
    @Column(name = "telephone")
    private String telephone;

    /**
     * 角色.
     **/
    @ApiModelProperty(hidden = true)
    @ManyToMany(mappedBy = "users")
    private Set<RoleEntity> roles;

    /** 部门机构. **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depart")
    private MapTreeEntity depart;

    /**
     * 创建人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user")
    @ApiModelProperty(hidden = true)
    private UserEntity createUser;

    /**
     * 修改人
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modify_user")
    @ApiModelProperty(hidden = true)
    private UserEntity modifyUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Date createDate = new Date();

    /**
     * 修改时间
     */
    @Column(name = "modify_time", nullable = true)
    private Date modifyDate;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UseStatus getStatus() {
        return status;
    }

    public void setStatus(UseStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public UserEntity getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserEntity createUser) {
        this.createUser = createUser;
    }

    public UserEntity getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(UserEntity modifyUser) {
        this.modifyUser = modifyUser;
    }


    public Boolean getIsResetPsd() {
        return isResetPsd;
    }

    public void setIsResetPsd(Boolean isResetPsd) {
        this.isResetPsd = isResetPsd;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public MapTreeEntity getDepart() {
        return depart;
    }

    public void setDepart(MapTreeEntity depart) {
        this.depart = depart;
    }
}
