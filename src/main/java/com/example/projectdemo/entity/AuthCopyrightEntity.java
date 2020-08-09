package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.ProductionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

/**
 * 授权作品_版权作品存证实体
 */
@ApiModel(value = "AuthCopyrightEntity")
@Entity
@Table(name = "auth_copyright")
@TypeDefs({
        @TypeDef(name = "productionType", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.ProductionType")})})
public class AuthCopyrightEntity extends UuidEntity {

    private static final long serialVersionUID = -7560120419553540557L;

    /**
     * 作品名称
     **/
    @Column(name = "production_name")
    private String productionName;

    /**
     * 创作人
     **/
    @Column(name = "production_composer")
    private String productionComposer;

    /**
     * 权利人（企业）名称
     **/
    @Column(name = "applicant_name")
    private String applicantName;

    /**
     * 身份证号码（纳税识别号）
     **/
    @Column(name = "identity_number")
    private String identityNumber;

    /**
     * 作品类型
     **/
    @Type(type = "productionType")
    @Column(name = "production_type")
    private ProductionType productionType;

    /**
     * 创作思路概述
     **/
    @Column(name = "create_thinking")
    private String createThinking;

    /**
     * 存证文件（文件，图片等）
     **/
    @Column(name = "save_file")
    private String saveFile;


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

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getProductionComposer() {
        return productionComposer;
    }

    public void setProductionComposer(String productionComposer) {
        this.productionComposer = productionComposer;
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

    public ProductionType getProductionType() {
        return productionType;
    }

    public void setProductionType(ProductionType productionType) {
        this.productionType = productionType;
    }

    public String getCreateThinking() {
        return createThinking;
    }

    public void setCreateThinking(String createThinking) {
        this.createThinking = createThinking;
    }

    public String getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
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
