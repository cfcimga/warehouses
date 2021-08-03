package com.managementsystem.service.impl;

import com.managementsystem.entity.Supplier;
import com.managementsystem.mapper.SupplierMapper;
import com.managementsystem.service.SupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Resource
    private SupplierMapper supplierMapper;
    @Override
    public List<Supplier> list(){
        return supplierMapper.findAll();
    }

    @Override
    public int addSupplier(Supplier supplier){
        return supplierMapper.addSupplier(supplier);
    }

    @Override
    public int editSupplier(Supplier supplier){
        return supplierMapper.editSupplier(supplier);
    }
    @Override
    public int deleteSupplier(String splId){
        return supplierMapper.deleteSupplier(splId);
    }

}
