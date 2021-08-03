package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goodsreceivednote implements Serializable {

    private static final long serialVersionUID = 1L;
    private String grnId;
    private String grnOrderNum;
    private Integer grnType;
    @TableField("grn_splId")
    private String grnSplId;

    private String grnSplName;
    @TableField("grn_materialId")
    private String grnMaterialId;
    private String grnMaterialName;

    private String warehouseId;
    private String warehouseName;
    private String reservoirareaId;
    private String reservoirareaName;

    private String grnSrId;
    private String grnSrName;
    private String grnDpId;

    @TableField("grn_inQuantity")
    private Long grnInQuantity;

    private Integer grnState;

    @TableField("grn_creatorId")
    private Integer grnCreatorId;

    private String grnComment;

    @TableField("grn_modifierId")
    private Integer grnModifierId;

    @TableField("grn_modificationTime")
    private String grnModificationTime;

    @TableField("grn_creationTime")
    private String grnCreationTime;

    private Supplier supplier;

    private Material material;

    private User user;

    public String getGrnDpId() {
        return grnDpId;
    }

    public void setGrnDpId(String grnDpId) {
        this.grnDpId = grnDpId;
    }

    public String getGrnSplName() {
        return grnSplName;
    }

    public void setGrnSplName(String grnSplName) {
        this.grnSplName = grnSplName;
    }

    public String getGrnMaterialName() {
        return grnMaterialName;
    }

    public void setGrnMaterialName(String grnMaterialName) {
        this.grnMaterialName = grnMaterialName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getReservoirareaId() {
        return reservoirareaId;
    }

    public void setReservoirareaId(String reservoirareaId) {
        this.reservoirareaId = reservoirareaId;
    }

    public String getReservoirareaName() {
        return reservoirareaName;
    }

    public void setReservoirareaName(String reservoirareaName) {
        this.reservoirareaName = reservoirareaName;
    }

    public String getGrnSrName() {
        return grnSrName;
    }

    public void setGrnSrName(String grnSrName) {
        this.grnSrName = grnSrName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getGrnOrderNum() {
        return grnOrderNum;
    }

    public void setGrnOrderNum(String grnOrderNum) {
        this.grnOrderNum = grnOrderNum;
    }

    public Integer getGrnType() {
        return grnType;
    }

    public void setGrnType(Integer grnType) {
        this.grnType = grnType;
    }

    public String getGrnSplId() {
        return grnSplId;
    }

    public void setGrnSplId(String grnSplId) {
        this.grnSplId = grnSplId;
    }

    public String getGrnMaterialId() {
        return grnMaterialId;
    }

    public void setGrnMaterialId(String grnMaterialId) {
        this.grnMaterialId = grnMaterialId;
    }

    public String getGrnSrId() {
        return grnSrId;
    }

    public void setGrnSrId(String grnSrId) {
        this.grnSrId = grnSrId;
    }

    public Long getGrnInQuantity() {
        return grnInQuantity;
    }

    public void setGrnInQuantity(Long grnInQuantity) {
        this.grnInQuantity = grnInQuantity;
    }

    public Integer getGrnState() {
        return grnState;
    }

    public void setGrnState(Integer grnState) {
        this.grnState = grnState;
    }

    public Integer getGrnCreatorId() {
        return grnCreatorId;
    }

    public void setGrnCreatorId(Integer grnCreatorId) {
        this.grnCreatorId = grnCreatorId;
    }

    public String getGrnComment() {
        return grnComment;
    }

    public void setGrnComment(String grnComment) {
        this.grnComment = grnComment;
    }

    public Integer getGrnModifierId() {
        return grnModifierId;
    }

    public void setGrnModifierId(Integer grnModifierId) {
        this.grnModifierId = grnModifierId;
    }

    public String getGrnModificationTime() {
        return grnModificationTime;
    }

    public void setGrnModificationTime(String grnModificationTime) {
        this.grnModificationTime = grnModificationTime;
    }

    public String getGrnCreationTime() {
        return grnCreationTime;
    }

    public void setGrnCreationTime(String grnCreationTime) {
        this.grnCreationTime = grnCreationTime;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
