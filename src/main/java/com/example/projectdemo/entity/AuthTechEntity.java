package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

/**
 * 授权作品_技术成果存证实体
 */
@ApiModel(value = "AuthTechEntity")
@Entity
@Table(name = "auth_tech")
@TypeDefs({
        @TypeDef(name = "techType", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.TechType")}),
        @TypeDef(name = "productionLevel", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.ProductionLevel")}),
        @TypeDef(name = "techProjectSource", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.TechProjectSource")}),
        @TypeDef(name = "evaluationWay", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.EvaluationWay")}),
        @TypeDef(name = "productionLevel", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.ProductionLevel")}),
        @TypeDef(name = "productionStage", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.ProductionStage")}),
        @TypeDef(name = "productionUseType", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.ProductionUseType")})})
public class AuthTechEntity extends UuidEntity {


    private static final long serialVersionUID = 4125312061538080554L;
    /**
     * 成果名称
     **/
    @Column(name = "production_name")
    private String productionName;

    /**
     * 第一完成单位
     **/
    @Column(name = "complete_depart")
    private String completeDepart;

    /**
     * 单位地址
     **/
    @Column(name = "depart_address")
    private String departAddress;

    /**
     * 身份证号码（纳税识别号）
     **/
    @Column(name = "identity_number")
    private String identityNumber;

    /**
     * 技术类型
     **/
    @Type(type = "techType")
    @Column(name = "tech_type")
    private TechType techType;

    /**
     * 成果水平
     **/
    @Type(type = "productionLevel")
    @Column(name = "production_level")
    private ProductionLevel productionLevel;

    /**
     * 技术课题来源
     **/
    @Type(type = "techProjectSource")
    @Column(name = "tech_project_source")
    private TechProjectSource techProjectSource;

    /**
     * 课题立项编号
     **/
    @Column(name = "project_number")
    private String projectNumber;

    /**
     * 评价方式
     **/
    @Type(type = "evaluationWay")
    @Column(name = "evaluation_way")
    private EvaluationWay evaluationWay;

    /**
     * 结题日期
     **/
    @Column(name = "finish_date")
    private Date finishDate;

    /**
     * 所处阶段
     **/
    @Type(type = "productionStage")
    @Column(name = "production_stage")
    private ProductionStage productionStage;

    /**
     * 应用状态
     **/
    @Type(type = "productionUseType")
    @Column(name = "production_use_type")
    private ProductionUseType productionUseType;

    /**
     * 成果主要完成人员
     **/
    @Column(name = "production_finish_person")
    private String productionFinishPerson;
    /**
     * 成果简介
     **/
    @Column(name = "production_brief")
    private String productionBrief;

    /**
     * 存证文件
     **/
    @Column(name = "save_file")
    private String saveFile;

    /**
     * 作品文件
     **/
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

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getCompleteDepart() {
        return completeDepart;
    }

    public void setCompleteDepart(String completeDepart) {
        this.completeDepart = completeDepart;
    }

    public String getDepartAddress() {
        return departAddress;
    }

    public void setDepartAddress(String departAddress) {
        this.departAddress = departAddress;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public TechType getTechType() {
        return techType;
    }

    public void setTechType(TechType techType) {
        this.techType = techType;
    }

    public ProductionLevel getProductionLevel() {
        return productionLevel;
    }

    public void setProductionLevel(ProductionLevel productionLevel) {
        this.productionLevel = productionLevel;
    }

    public TechProjectSource getTechProjectSource() {
        return techProjectSource;
    }

    public void setTechProjectSource(TechProjectSource techProjectSource) {
        this.techProjectSource = techProjectSource;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public EvaluationWay getEvaluationWay() {
        return evaluationWay;
    }

    public void setEvaluationWay(EvaluationWay evaluationWay) {
        this.evaluationWay = evaluationWay;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public ProductionStage getProductionStage() {
        return productionStage;
    }

    public void setProductionStage(ProductionStage productionStage) {
        this.productionStage = productionStage;
    }

    public ProductionUseType getProductionUseType() {
        return productionUseType;
    }

    public void setProductionUseType(ProductionUseType productionUseType) {
        this.productionUseType = productionUseType;
    }

    public String getProductionFinishPerson() {
        return productionFinishPerson;
    }

    public void setProductionFinishPerson(String productionFinishPerson) {
        this.productionFinishPerson = productionFinishPerson;
    }

    public String getProductionBrief() {
        return productionBrief;
    }

    public void setProductionBrief(String productionBrief) {
        this.productionBrief = productionBrief;
    }

    public String getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
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
