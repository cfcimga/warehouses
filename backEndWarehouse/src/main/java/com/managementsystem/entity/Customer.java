package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ctmId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCtmId() {
        return ctmId;
    }

    public void setCtmId(String ctmId) {
        this.ctmId = ctmId;
    }

    public String getCtmName() {
        return ctmName;
    }

    public void setCtmName(String ctmName) {
        this.ctmName = ctmName;
    }

    public Integer getCtmType() {
        return ctmType;
    }

    public void setCtmType(Integer ctmType) {
        this.ctmType = ctmType;
    }

    public String getCtmPhone() {
        return ctmPhone;
    }

    public void setCtmPhone(String ctmPhone) {
        this.ctmPhone = ctmPhone;
    }

    public String getCtmLinkman() {
        return ctmLinkman;
    }

    public void setCtmLinkman(String ctmLinkman) {
        this.ctmLinkman = ctmLinkman;
    }

    public Integer getCtmCreatorId() {
        return ctmCreatorId;
    }

    public void setCtmCreatorId(Integer ctmCreatorId) {
        this.ctmCreatorId = ctmCreatorId;
    }

    public String getCtmComment() {
        return ctmComment;
    }

    public void setCtmComment(String ctmComment) {
        this.ctmComment = ctmComment;
    }

    public Integer getCtmModifierId() {
        return ctmModifierId;
    }

    public void setCtmModifierId(Integer ctmModifierId) {
        this.ctmModifierId = ctmModifierId;
    }

    public String getCtmModificationTime() {
        return ctmModificationTime;
    }

    public void setCtmModificationTime(String ctmModificationTime) {
        this.ctmModificationTime = ctmModificationTime;
    }

    public String getCtmCreationTime() {
        return ctmCreationTime;
    }

    public void setCtmCreationTime(String ctmCreationTime) {
        this.ctmCreationTime = ctmCreationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String ctmName;

    private Integer ctmType;

    private String ctmPhone;

    private String ctmLinkman;

    private Integer ctmCreatorId;

    private String ctmComment;

    private Integer ctmModifierId;

    private String ctmModificationTime;

    private String ctmCreationTime;

    private User user;



}
