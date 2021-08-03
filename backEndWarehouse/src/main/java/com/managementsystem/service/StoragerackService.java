package com.managementsystem.service;

import com.managementsystem.entity.Storagerack;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StoragerackService extends IService<Storagerack> {

    List<Storagerack> list();

    int addStoragerack(Storagerack storagerack);

    int editStoragerack(Storagerack storagerack);

    int deleteStoragerack(String srId);

    List<Storagerack> getStoragerackBySrRaId(String raId);

    int updateMaterial(String mtId,String mtName, String srId);

    int clearMaterial(String srId);
}
