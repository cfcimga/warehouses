package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Carrier implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cId;

    private String cName;

    private Integer cType;

    private String cPhone;

    private String cLinkman;

    private Integer cCreatorId;

    private String cComment;

    private Integer cModifierId;

    private String cModificationTime;

    private String cCreationTime;

    private User user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    public String getcLinkman() {
        return cLinkman;
    }

    public void setcLinkman(String cLinkman) {
        this.cLinkman = cLinkman;
    }

    public Integer getcCreatorId() {
        return cCreatorId;
    }

    public void setcCreatorId(Integer cCreatorId) {
        this.cCreatorId = cCreatorId;
    }

    public String getcComment() {
        return cComment;
    }

    public void setcComment(String cComment) {
        this.cComment = cComment;
    }

    public Integer getcModifierId() {
        return cModifierId;
    }

    public void setcModifierId(Integer cModifierId) {
        this.cModifierId = cModifierId;
    }

    public String getcModificationTime() {
        return cModificationTime;
    }

    public void setcModificationTime(String cModificationTime) {
        this.cModificationTime = cModificationTime;
    }

    public String getcCreationTime() {
        return cCreationTime;
    }

    public void setcCreationTime(String cCreationTime) {
        this.cCreationTime = cCreationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
