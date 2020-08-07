package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.TechProjectSource;
import com.example.projectdemo.common.enums.TechType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

/**
 * 原创作品_技术成果存证实体
 */
@ApiModel(value = "OriginalTechEntity")
@Entity
@Table(name = "original_tech")
@TypeDefs({
        @TypeDef(name = "techType", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.TechType")}),
        @TypeDef(name = "techProjectSource", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.TechProjectSource")})})
public class OriginalTechEntity extends UuidEntity {

    private static final long serialVersionUID = 2585993358033735754L;
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
     * 身份证号码（纳税识别号）
     **/
    @Column(name = "identity_number")
    private String identityNumber;

    /**
     * 技术类型
     **/
    @Column(name = "tech_type")
    private TechType techType;

    /**
     * 技术课题来源
     **/
    @Column(name = "tech_project_source")
    private TechProjectSource techProjectSource;

    /**
     * 存证文件
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

    public String getCompleteDepart() {
        return completeDepart;
    }

    public void setCompleteDepart(String completeDepart) {
        this.completeDepart = completeDepart;
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

    public TechProjectSource getTechProjectSource() {
        return techProjectSource;
    }

    public void setTechProjectSource(TechProjectSource techProjectSource) {
        this.techProjectSource = techProjectSource;
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
