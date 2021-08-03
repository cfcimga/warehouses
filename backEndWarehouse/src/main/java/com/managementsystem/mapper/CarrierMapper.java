package com.managementsystem.mapper;

import com.managementsystem.entity.Carrier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


public interface CarrierMapper extends BaseMapper<Carrier> {

    List<Carrier> findAll();

    List<Carrier> findCreator();

    int addCarrier(Carrier carrier);

    int editCarrier(Carrier carrier);

    int deleteCarrier(@Param("cId")String cId);
}
