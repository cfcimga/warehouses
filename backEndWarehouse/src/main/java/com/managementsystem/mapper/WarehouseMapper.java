package com.managementsystem.mapper;

import com.managementsystem.entity.Warehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface WarehouseMapper extends BaseMapper<Warehouse> {

    List<Warehouse> findAll();

    int addWarehouse(Warehouse warehouse);

    int editWarehouse(Warehouse warehouse);

    int deleteWarehouse(@Param("whId")String whId);

    Warehouse getWarehouseById(@Param("whId")String whId);
}
