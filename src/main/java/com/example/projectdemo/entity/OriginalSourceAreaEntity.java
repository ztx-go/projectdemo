package com.example.projectdemo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 原创作品_原产地存证
 */
@ApiModel(value = "OriginalSourceAreaEntity")
@Entity
@Table(name = "original_sourceArea")
public class OriginalSourceAreaEntity extends  UuidEntity{

    private static final long serialVersionUID = -5388420283571866540L;

    /**
     * 货品名称
     **/
    @Column(name = "production_name")
    private String productionName;

    /**
     * 货品产地
     **/
    @Column(name = "brand_name")
    private String productionArea;

    /**
     * 出口单位名称
     **/
    @Column(name = "export_depart")
    private String exportDepart;

    /**
     * 货品生产程序（文件、图片等）
     **/
    @Column(name = "production_procedure")
    private String productionProcedure;

    /**
     * 货品成分（文件、图片等）
     **/
    @Column(name = "production_element")
    private String productionElement;

    /**
     * 证明文字
     **/
    @Column(name = "testify_note")
    private String testifyNote;

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

    public String getProductionArea() {
        return productionArea;
    }

    public void setProductionArea(String productionArea) {
        this.productionArea = productionArea;
    }

    public String getExportDepart() {
        return exportDepart;
    }

    public void setExportDepart(String exportDepart) {
        this.exportDepart = exportDepart;
    }

    public String getProductionProcedure() {
        return productionProcedure;
    }

    public void setProductionProcedure(String productionProcedure) {
        this.productionProcedure = productionProcedure;
    }

    public String getProductionElement() {
        return productionElement;
    }

    public void setProductionElement(String productionElement) {
        this.productionElement = productionElement;
    }

    public String getTestifyNote() {
        return testifyNote;
    }

    public void setTestifyNote(String testifyNote) {
        this.testifyNote = testifyNote;
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
