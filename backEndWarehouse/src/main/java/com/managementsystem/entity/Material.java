package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mtId;
    private String mtName;
    private Integer mtType;

    private Integer mtSize;

    @TableField("mt_creatorId")
    private Integer mtCreatorId;

    private String mtComment;

    @TableField("mt_modifierId")
    private Integer mtModifierId;

    @TableField("mt_modificationTime")
    private String mtModificationTime;

    @TableField("mt_creationTime")
    private String mtCreationTime;

    private List<Warehouse> warehouse;

    private List<Reservoirarea> reservoirarea;

    private List<Storagerack> storagerack;

    private User user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMtId() {
        return mtId;
    }

    public void setMtId(String mtId) {
        this.mtId = mtId;
    }

    public String getMtName() {
        return mtName;
    }

    public void setMtName(String mtName) {
        this.mtName = mtName;
    }

    public Integer getMtType() {
        return mtType;
    }

    public void setMtType(Integer mtType) {
        this.mtType = mtType;
    }

    public Integer getMtSize() {
        return mtSize;
    }

    public void setMtSize(Integer mtSize) {
        this.mtSize = mtSize;
    }

    public Integer getMtCreatorId() {
        return mtCreatorId;
    }

    public void setMtCreatorId(Integer mtCreatorId) {
        this.mtCreatorId = mtCreatorId;
    }

    public String getMtComment() {
        return mtComment;
    }

    public void setMtComment(String mtComment) {
        this.mtComment = mtComment;
    }

    public Integer getMtModifierId() {
        return mtModifierId;
    }

    public void setMtModifierId(Integer mtModifierId) {
        this.mtModifierId = mtModifierId;
    }

    public String getMtModificationTime() {
        return mtModificationTime;
    }

    public void setMtModificationTime(String mtModificationTime) {
        this.mtModificationTime = mtModificationTime;
    }

    public String getMtCreationTime() {
        return mtCreationTime;
    }

    public void setMtCreationTime(String mtCreationTime) {
        this.mtCreationTime = mtCreationTime;
    }
}
