package com.managementsystem.service;

import com.managementsystem.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SupplierService extends IService<Supplier> {

    List<Supplier> list();

    int addSupplier(Supplier supplier);

    int editSupplier(Supplier supplier);

    int deleteSupplier(String splId);
}
