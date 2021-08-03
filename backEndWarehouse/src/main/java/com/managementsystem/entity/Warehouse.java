package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String whId;

    private String whName;

    @TableField("wh_creatorId")
    private Integer whCreatorId;

    private String whComment;

    @TableField("wh_modifierId")
    private Integer whModifierId;

    @TableField("wh_modificationTime")
    private String whModificationTime;

    @TableField("wh_creationTime")
    private String whCreationTime;

    private User user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public Integer getWhCreatorId() {
        return whCreatorId;
    }

    public void setWhCreatorId(Integer whCreatorId) {
        this.whCreatorId = whCreatorId;
    }

    public String getWhComment() {
        return whComment;
    }

    public void setWhComment(String whComment) {
        this.whComment = whComment;
    }

    public Integer getWhModifierId() {
        return whModifierId;
    }

    public void setWhModifierId(Integer whModifierId) {
        this.whModifierId = whModifierId;
    }

    public String getWhModificationTime() {
        return whModificationTime;
    }

    public void setWhModificationTime(String whModificationTime) {
        this.whModificationTime = whModificationTime;
    }

    public String getWhCreationTime() {
        return whCreationTime;
    }

    public void setWhCreationTime(String whCreationTime) {
        this.whCreationTime = whCreationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
