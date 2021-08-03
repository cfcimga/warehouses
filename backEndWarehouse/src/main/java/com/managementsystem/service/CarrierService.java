package com.managementsystem.service;

import com.managementsystem.entity.Carrier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface CarrierService extends IService<Carrier> {

     List<Carrier> list() ;

     List<Carrier> findCreator();

     int addCarrier(Carrier carrier);

     int editCarrier(Carrier carrier);

     int deleteCarrier(String cId);
}
