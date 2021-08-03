package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Storagerack implements Serializable {

    private static final long serialVersionUID = 1L;

    private String srId;

    private String srName;

    private String srMtId;

    private String srMtName;

    @TableField("sr_whId")
    private String srWhId;


    private String srWhName;

    @TableField("sr_raId")
    private String srRaId;

    private String srRaName;

    @TableField("sr_creatorId")
    private Integer srCreatorId;

    private String srComment;

    @TableField("sr_modifierId")
    private Integer srModifierId;

    @TableField("sr_modificationTime")
    private String srModificationTime;

    @TableField("sr_creationTime")
    private String srCreationTime;

    private Warehouse warehouse;

    private Reservoirarea reservoirarea;

    private User user;

    public String getSrMtName() {
        return srMtName;
    }

    public void setSrMtName(String srMtName) {
        this.srMtName = srMtName;
    }

    public String getSrWhName() {
        return srWhName;
    }

    public void setSrWhName(String srWhName) {
        this.srWhName = srWhName;
    }

    public String getSrRaName() {
        return srRaName;
    }

    public void setSrRaName(String srRaName) {
        this.srRaName = srRaName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public String getSrName() {
        return srName;
    }

    public void setSrName(String srName) {
        this.srName = srName;
    }

    public String getSrMtId() {
        return srMtId;
    }

    public void setSrMtId(String srMtId) {
        this.srMtId = srMtId;
    }

    public String getSrWhId() {
        return srWhId;
    }

    public void setSrWhId(String srWhId) {
        this.srWhId = srWhId;
    }

    public String getSrRaId() {
        return srRaId;
    }

    public void setSrRaId(String srRaId) {
        this.srRaId = srRaId;
    }

    public Integer getSrCreatorId() {
        return srCreatorId;
    }

    public void setSrCreatorId(Integer srCreatorId) {
        this.srCreatorId = srCreatorId;
    }

    public String getSrComment() {
        return srComment;
    }

    public void setSrComment(String srComment) {
        this.srComment = srComment;
    }

    public Integer getSrModifierId() {
        return srModifierId;
    }

    public void setSrModifierId(Integer srModifierId) {
        this.srModifierId = srModifierId;
    }

    public String getSrModificationTime() {
        return srModificationTime;
    }

    public void setSrModificationTime(String srModificationTime) {
        this.srModificationTime = srModificationTime;
    }

    public String getSrCreationTime() {
        return srCreationTime;
    }

    public void setSrCreationTime(String srCreationTime) {
        this.srCreationTime = srCreationTime;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Reservoirarea getReservoirarea() {
        return reservoirarea;
    }

    public void setReservoirarea(Reservoirarea reservoirarea) {
        this.reservoirarea = reservoirarea;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
