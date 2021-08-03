package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Dispatchlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("dpId")
    private String dpId;
    @TableField("dpOdlId")
    private String dpOdlId;
    @TableField("dpTime")
    private String dpTime;
    @TableField("dpCId")
    private String dpCId;
    @TableField("dpState")
    private Integer dpState;

    private String expressNumber;


    @TableField("dpCreatorId")
    private Integer dpCreatorId;

    @TableField("dpComment")
    private String dpComment;

    @TableField("dpModifierId")
    private Integer dpModifierId;

    @TableField("dpModificationTime")
    private String dpModificationTime;

    @TableField("dpCreationTime")
    private String dpCreationTime;

    private Outdeliverlist outdeliverlist;

    private Carrier carrier;

    private User user;

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDpOdlId() {
        return dpOdlId;
    }

    public void setDpOdlId(String dpOdlId) {
        this.dpOdlId = dpOdlId;
    }

    public String getDpTime() {
        return dpTime;
    }

    public void setDpTime(String dpTime) {
        this.dpTime = dpTime;
    }

    public String getDpCId() {
        return dpCId;
    }

    public void setDpCId(String dpCId) {
        this.dpCId = dpCId;
    }

    public Integer getDpState() {
        return dpState;
    }

    public void setDpState(Integer dpState) {
        this.dpState = dpState;
    }

    public Integer getDpCreatorId() {
        return dpCreatorId;
    }

    public void setDpCreatorId(Integer dpCreatorId) {
        this.dpCreatorId = dpCreatorId;
    }

    public String getDpComment() {
        return dpComment;
    }

    public void setDpComment(String dpComment) {
        this.dpComment = dpComment;
    }

    public Integer getDpModifierId() {
        return dpModifierId;
    }

    public void setDpModifierId(Integer dpModifierId) {
        this.dpModifierId = dpModifierId;
    }

    public String getDpModificationTime() {
        return dpModificationTime;
    }

    public void setDpModificationTime(String dpModificationTime) {
        this.dpModificationTime = dpModificationTime;
    }

    public String getDpCreationTime() {
        return dpCreationTime;
    }

    public void setDpCreationTime(String dpCreationTime) {
        this.dpCreationTime = dpCreationTime;
    }

    public Outdeliverlist getOutdeliverlist() {
        return outdeliverlist;
    }

    public void setOutdeliverlist(Outdeliverlist outdeliverlist) {
        this.outdeliverlist = outdeliverlist;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
