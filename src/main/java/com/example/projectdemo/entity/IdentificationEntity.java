package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.CertificationType;
import com.example.projectdemo.common.enums.EnterpriseProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;


/**
 * 实名认证
 */
@ApiModel(value = "IdentificationEntity")
@Entity
@Table(name = "identification")
@TypeDefs({
        @TypeDef(name = "certification", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.CertificationType")}),
        @TypeDef(name = "enterpriseProperty", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.EnterpriseProperty")})})
public class IdentificationEntity extends UuidEntity {

    private static final long serialVersionUID = 3414984331637870959L;
    /**
     * 实名认证
     */
    @Type(type = "certification")
    @Column(name = "certification")
    private CertificationType certification;

    /**
     * 企业属性
     */
    @Type(type = "enterpriseProperty")
    @Column(name = "enterprise_property")
    private EnterpriseProperty enterpriseProperty;

    /**
     * 企业全称
     */
    @Column(name = "enterprise_name")
    private String enterpriseName;

    /**
     * 企业营业执照
     */
    @Column(name = "enterprise_license")
    private String enterpriseLicense;

    /**
     * 企业机构代码
     */
    @Column(name = "institution_code")
    private String institutionCode;

    /**
     * 企业法人代表
     */
    @Column(name = "legal_representative")
    private String legalRepresentative;

    /**
     * 企业法人身份证
     */
    @Column(name = "legal_identity_cards")
    private String legalIdentityCards;

    /**
     * 是否审核通过
     */
    @Column(name = "is_audit")
    private Boolean audit;

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
    @Column(name = "modify_time")
    private Date modifyDate;

    public CertificationType getCertification() {
        return certification;
    }

    public void setCertification(CertificationType certification) {
        this.certification = certification;
    }

    public EnterpriseProperty getEnterpriseProperty() {
        return enterpriseProperty;
    }

    public void setEnterpriseProperty(EnterpriseProperty enterpriseProperty) {
        this.enterpriseProperty = enterpriseProperty;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseLicense() {
        return enterpriseLicense;
    }

    public void setEnterpriseLicense(String enterpriseLicense) {
        this.enterpriseLicense = enterpriseLicense;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getLegalIdentityCards() {
        return legalIdentityCards;
    }

    public void setLegalIdentityCards(String legalIdentityCards) {
        this.legalIdentityCards = legalIdentityCards;
    }

    public Boolean getAudit() {
        return audit;
    }

    public void setAudit(Boolean audit) {
        this.audit = audit;
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
}
