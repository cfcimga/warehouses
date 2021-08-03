package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Inform implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("fId")
    private String fId;

    @TableField("fTitle")
    private String fTitle;

    @TableField("fContent")
    private String fContent;

    @TableField("fCreatorId")
    private String fCreatorId;

    @TableField("fCreationTime")
    private String fCreationTime;

    private List<Mdform> mdform;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getfTitle() {
        return fTitle;
    }

    public void setfTitle(String fTitle) {
        this.fTitle = fTitle;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }

    public String getfCreatorId() {
        return fCreatorId;
    }

    public void setfCreatorId(String fCreatorId) {
        this.fCreatorId = fCreatorId;
    }

    public String getfCreationTime() {
        return fCreationTime;
    }

    public void setfCreationTime(String fCreationTime) {
        this.fCreationTime = fCreationTime;
    }

    public List<Mdform> getMdform() {
        return mdform;
    }

    public void setMdform(List<Mdform> mdform) {
        this.mdform = mdform;
    }
}
