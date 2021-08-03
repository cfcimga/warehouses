package com.managementsystem.mapper;

import com.managementsystem.entity.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SupplierMapper extends BaseMapper<Supplier> {
    List<Supplier> findAll();

    int addSupplier(Supplier supplier);

    int editSupplier(Supplier supplier);

    int deleteSupplier(@Param("splId")String splId);
}
