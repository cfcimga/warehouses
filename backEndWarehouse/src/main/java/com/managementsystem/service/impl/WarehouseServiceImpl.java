package com.managementsystem.service.impl;

import com.managementsystem.entity.Warehouse;
import com.managementsystem.mapper.WarehouseMapper;
import com.managementsystem.service.WarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Override
    public List<Warehouse> list(){
        return warehouseMapper.findAll();
    }

    @Override
    public int addWarehouse(Warehouse warehouse){
        return warehouseMapper.addWarehouse(warehouse);
    }
    @Override
    public int editWarehouse(Warehouse warehouse){
        return warehouseMapper.editWarehouse(warehouse);
    }
    @Override
    public int deleteWarehouse(String whId){
        return warehouseMapper.deleteWarehouse(whId);
    }

    @Override
    public Warehouse getWarehouseById(String whId){
        return warehouseMapper.getWarehouseById(whId);
    }
}
