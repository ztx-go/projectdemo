package com.example.projectdemo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * 原创作品_商标和商号存证
 */
@ApiModel(value = "OriginalBrandEntity")
@Entity
@Table(name = "original_brand")
public class OriginalBrandEntity extends UuidEntity{

    private static final long serialVersionUID = -8934358614612466868L;

    /**
     * 商标（商号）名称
     **/
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 申请人/企业名称
     **/
    @Column(name = "applicant_name")
    private String applicantName;

    /**
     * 身份证号码（纳税识别号）
     **/
    @Column(name = "identity_number")
    private String identityNumber;
    /**
     * 商标（商号）类别
     **/
    @Column(name = "brand_type")
    private String brandType;

    /**
     * 创作思路概述
     **/
    @Column(name = "create_thinking")
    private String createThinking;

    /**
     * 作品文件（文件，图片等）
     */
    @Column(name = "production_file")
    private String productionFile;

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getCreateThinking() {
        return createThinking;
    }

    public void setCreateThinking(String createThinking) {
        this.createThinking = createThinking;
    }

    public String getProductionFile() {
        return productionFile;
    }

    public void setProductionFile(String productionFile) {
        this.productionFile = productionFile;
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
