package com.example.projectdemo.entity;

import com.example.projectdemo.common.enums.SourceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

/**
 * 原创作品_传统知识存证和保护
 */
@ApiModel(value = "OriginalTraditionalEntity")
@Entity
@Table(name = "original_traditional")
@TypeDefs({
        @TypeDef(name = "sourceType", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.SourceType")})})
public class OriginalTraditionalEntity extends UuidEntity {

    private static final long serialVersionUID = -7988138590401179909L;
    /**
     * 来源
     **/
    @Type(type = "sourceType")
    @Column(name = "source_type")
    private SourceType sourceType;

    /**
     * 流传证据
     **/
    @Column(name = "spread_evidence")
    private String spreadEvidence;

    /**
     * 流传时间
     **/
    @Column(name = "spread_time")
    private String spreadTime;

    /**
     * 流传期间改进点
     **/
    @Column(name = "spread_improve")
    private String spreadImprove;

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

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getSpreadEvidence() {
        return spreadEvidence;
    }

    public void setSpreadEvidence(String spreadEvidence) {
        this.spreadEvidence = spreadEvidence;
    }

    public String getSpreadTime() {
        return spreadTime;
    }

    public void setSpreadTime(String spreadTime) {
        this.spreadTime = spreadTime;
    }

    public String getSpreadImprove() {
        return spreadImprove;
    }

    public void setSpreadImprove(String spreadImprove) {
        this.spreadImprove = spreadImprove;
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
