package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    private String splId;

    private String splName;

    private Integer splType;

    private String splPhone;

    private String splLinkman;

    @TableField("spl_creatorId")
    private Integer splCreatorId;

    private String splComment;

    @TableField("spl_modifierId")
    private Integer splModifierId;

    @TableField("spl_modificationTime")
    private String splModificationTime;

    @TableField("spl_creationTime")
    private String splCreationTime;

    private User user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSplId() {
        return splId;
    }

    public void setSplId(String splId) {
        this.splId = splId;
    }

    public String getSplName() {
        return splName;
    }

    public void setSplName(String splName) {
        this.splName = splName;
    }

    public Integer getSplType() {
        return splType;
    }

    public void setSplType(Integer splType) {
        this.splType = splType;
    }

    public String getSplPhone() {
        return splPhone;
    }

    public void setSplPhone(String splPhone) {
        this.splPhone = splPhone;
    }

    public String getSplLinkman() {
        return splLinkman;
    }

    public void setSplLinkman(String splLinkman) {
        this.splLinkman = splLinkman;
    }

    public Integer getSplCreatorId() {
        return splCreatorId;
    }

    public void setSplCreatorId(Integer splCreatorId) {
        this.splCreatorId = splCreatorId;
    }

    public String getSplComment() {
        return splComment;
    }

    public void setSplComment(String splComment) {
        this.splComment = splComment;
    }

    public Integer getSplModifierId() {
        return splModifierId;
    }

    public void setSplModifierId(Integer splModifierId) {
        this.splModifierId = splModifierId;
    }

    public String getSplModificationTime() {
        return splModificationTime;
    }

    public void setSplModificationTime(String splModificationTime) {
        this.splModificationTime = splModificationTime;
    }

    public String getSplCreationTime() {
        return splCreationTime;
    }

    public void setSplCreationTime(String splCreationTime) {
        this.splCreationTime = splCreationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
