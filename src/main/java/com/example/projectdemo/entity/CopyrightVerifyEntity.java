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
 * 版权确认
 */
@ApiModel(value = "CopyrightVerifyEntity")
@Entity
@Table(name = "copyright_verify")
@TypeDefs({
        @TypeDef(name = "productionType", typeClass = EnumType.class, parameters = {
                @Parameter(name = "class", value = "com.example.projectdemo.common.enums.ProductionType")})})
public class CopyrightVerifyEntity extends UuidEntity {

    private static final long serialVersionUID = 5318612731424368075L;

    /**
     *信息上链哈希
     */
    @Column(name = "message_chain_hash")
    private String messageChainHash;

    /**
     * 上链区块
     */
    @Column(name = "chain_block")
    private String chainBlock;

    /**
     * 登记号
     */
    @Column(name = "register_number")
    private String registerNumber;

    /**
     * 作品类型
     **/
    @Type(type = "productionType")
    @Column(name = "production_type")
    private ProductionType productionType;

    /**
     * 作品名称
     */
    @Column(name = "production_name")
    private String productionName;

    /**
     * 作者名称
     */
    @Column(name = "author_name")
    private String authorName;

    /**
     * 作品名称
     */
    @Column(name = "production_check")
    private String productionCheck;

    /**
     * 登记证明
     */
    @Column(name = "register_certificate")
    private String  registerCertificate;

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

    public String getMessageChainHash() {
        return messageChainHash;
    }

    public void setMessageChainHash(String messageChainHash) {
        this.messageChainHash = messageChainHash;
    }

    public String getChainBlock() {
        return chainBlock;
    }

    public void setChainBlock(String chainBlock) {
        this.chainBlock = chainBlock;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public ProductionType getProductionType() {
        return productionType;
    }

    public void setProductionType(ProductionType productionType) {
        this.productionType = productionType;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getProductionCheck() {
        return productionCheck;
    }

    public void setProductionCheck(String productionCheck) {
        this.productionCheck = productionCheck;
    }

    public String getRegisterCertificate() {
        return registerCertificate;
    }

    public void setRegisterCertificate(String registerCertificate) {
        this.registerCertificate = registerCertificate;
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
