package com.managementsystem.service.impl;

import com.managementsystem.entity.Carrier;
import com.managementsystem.mapper.CarrierMapper;
import com.managementsystem.service.CarrierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CarrierServiceImpl extends ServiceImpl<CarrierMapper, Carrier> implements CarrierService {

    @Resource
    private CarrierMapper carrierMapper;

    @Override
    public List<Carrier> list(){
        return carrierMapper.findAll();
    }

    @Override
    public List<Carrier> findCreator(){
        return carrierMapper.findCreator();
    }

    @Override
    public int addCarrier(Carrier carrier){
        return carrierMapper.addCarrier(carrier);
    }
    @Override
    public int editCarrier(Carrier carrier){
        return carrierMapper.editCarrier(carrier);
    }
    @Override
    public int deleteCarrier(String cId){
        return carrierMapper.deleteCarrier(cId);
    }
}
