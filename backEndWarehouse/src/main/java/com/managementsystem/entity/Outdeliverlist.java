package com.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Outdeliverlist implements Serializable {

    private static final long serialVersionUID = 1L;
    private String odlId;
    private String odlOrderNum;
    private String odlType;
    @TableField("odl_customerId")
    private String odlCustomerId;
    private String odlCustomerName;

    private String odlMaterialName ;
    private String warehouseId;
    private String warehouseName;
    private String reservoirareaId;
    private String reservoirareaName;

    private String odlSrId;
    private String odlSrName;

    @TableField("odl_materialId")
    private String odlMaterialId;

    @TableField("odl_outQuantity")
    private Long odlOutQuantity;

    private Integer odlState;

    private String odlAddress;

    @TableField("odl_creatorId")
    private Integer odlCreatorId;
    private String odlComment;

    @TableField("odl_modifierId")
    private Integer odlModifierId;

    @TableField("odl_modificationTime")
    private String odlModificationTime;

    @TableField("odl_creationTime")
    private String odlCreationTime;

    private Customer customer;

    private Material material;

    private User user;

    public String getOdlAddress() {
        return odlAddress;
    }

    public void setOdlAddress(String odlAddress) {
        this.odlAddress = odlAddress;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOdlId() {
        return odlId;
    }

    public void setOdlId(String odlId) {
        this.odlId = odlId;
    }

    public String getOdlOrderNum() {
        return odlOrderNum;
    }

    public void setOdlOrderNum(String odlOrderNum) {
        this.odlOrderNum = odlOrderNum;
    }

    public String getOdlType() {
        return odlType;
    }

    public void setOdlType(String odlType) {
        this.odlType = odlType;
    }

    public String getOdlCustomerId() {
        return odlCustomerId;
    }

    public void setOdlCustomerId(String odlCustomerId) {
        this.odlCustomerId = odlCustomerId;
    }

    public String getOdlCustomerName() {
        return odlCustomerName;
    }

    public void setOdlCustomerName(String odlCustomerName) {
        this.odlCustomerName = odlCustomerName;
    }

    public String getOdlMaterialName() {
        return odlMaterialName;
    }

    public void setOdlMaterialName(String odlMaterialName) {
        this.odlMaterialName = odlMaterialName;
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

    public String getOdlSrId() {
        return odlSrId;
    }

    public void setOdlSrId(String odlSrId) {
        this.odlSrId = odlSrId;
    }

    public String getOdlSrName() {
        return odlSrName;
    }

    public void setOdlSrName(String odlSrName) {
        this.odlSrName = odlSrName;
    }

    public String getOdlMaterialId() {
        return odlMaterialId;
    }

    public void setOdlMaterialId(String odlMaterialId) {
        this.odlMaterialId = odlMaterialId;
    }

    public Long getOdlOutQuantity() {
        return odlOutQuantity;
    }

    public void setOdlOutQuantity(Long odlOutQuantity) {
        this.odlOutQuantity = odlOutQuantity;
    }

    public Integer getOdlState() {
        return odlState;
    }

    public void setOdlState(Integer odlState) {
        this.odlState = odlState;
    }

    public Integer getOdlCreatorId() {
        return odlCreatorId;
    }

    public void setOdlCreatorId(Integer odlCreatorId) {
        this.odlCreatorId = odlCreatorId;
    }

    public String getOdlComment() {
        return odlComment;
    }

    public void setOdlComment(String odlComment) {
        this.odlComment = odlComment;
    }

    public Integer getOdlModifierId() {
        return odlModifierId;
    }

    public void setOdlModifierId(Integer odlModifierId) {
        this.odlModifierId = odlModifierId;
    }

    public String getOdlModificationTime() {
        return odlModificationTime;
    }

    public void setOdlModificationTime(String odlModificationTime) {
        this.odlModificationTime = odlModificationTime;
    }

    public String getOdlCreationTime() {
        return odlCreationTime;
    }

    public void setOdlCreationTime(String odlCreationTime) {
        this.odlCreationTime = odlCreationTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
