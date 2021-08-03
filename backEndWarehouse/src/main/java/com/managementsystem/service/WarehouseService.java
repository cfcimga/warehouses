package com.managementsystem.service;

import com.managementsystem.entity.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;


public interface WarehouseService extends IService<Warehouse> {

    List<Warehouse> list();

    int addWarehouse(Warehouse warehouse);

    int editWarehouse(Warehouse warehouse);

    int deleteWarehouse(String whId);

    Warehouse getWarehouseById(String whId);
}
