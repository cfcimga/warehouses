package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Reservoirarea implements Serializable {

    private static final long serialVersionUID = 1L;

    private String raId;

    private String raName;

    private Integer raType;

    @TableField("ra_whId")
    private String raWhId;

    private String raWhName;

    @TableField("ra_creatorId")
    private Integer raCreatorId;

    private String raComment;

    @TableField("ra_modifierId")
    private Integer raModifierId;

    @TableField("ra_modificationTime")
    private String raModificationTime;

    @TableField("ra_creationTime")
    private String raCreationTime;

    private Warehouse warehouse;

    private User user;

    public String getRaWhName() {
        return raWhName;
    }

    public void setRaWhName(String raWhName) {
        this.raWhName = raWhName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRaId() {
        return raId;
    }

    public void setRaId(String raId) {
        this.raId = raId;
    }

    public String getRaName() {
        return raName;
    }

    public void setRaName(String raName) {
        this.raName = raName;
    }

    public Integer getRaType() {
        return raType;
    }

    public void setRaType(Integer raType) {
        this.raType = raType;
    }

    public String getRaWhId() {
        return raWhId;
    }

    public void setRaWhId(String raWhId) {
        this.raWhId = raWhId;
    }

    public Integer getRaCreatorId() {
        return raCreatorId;
    }

    public void setRaCreatorId(Integer raCreatorId) {
        this.raCreatorId = raCreatorId;
    }

    public String getRaComment() {
        return raComment;
    }

    public void setRaComment(String raComment) {
        this.raComment = raComment;
    }

    public Integer getRaModifierId() {
        return raModifierId;
    }

    public void setRaModifierId(Integer raModifierId) {
        this.raModifierId = raModifierId;
    }

    public String getRaModificationTime() {
        return raModificationTime;
    }

    public void setRaModificationTime(String raModificationTime) {
        this.raModificationTime = raModificationTime;
    }

    public String getRaCreationTime() {
        return raCreationTime;
    }

    public void setRaCreationTime(String raCreationTime) {
        this.raCreationTime = raCreationTime;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
