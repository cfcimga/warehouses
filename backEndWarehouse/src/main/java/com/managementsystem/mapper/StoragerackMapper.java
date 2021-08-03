package com.managementsystem.mapper;

import com.managementsystem.entity.Storagerack;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StoragerackMapper extends BaseMapper<Storagerack> {

    List<Storagerack> findAll();

    int addStoragerack(Storagerack storagerack);

    int editStoragerack(Storagerack storagerack);

    int deleteStoragerack(@Param("srId")String srId);

    List<Storagerack> getStoragerackBySrRaId(@Param("raId") String raId);

    int updateMaterial(@Param("mtId") String mtId,@Param("mtName") String mtName,@Param("srId") String srId);

    int clearMaterial(@Param("srId") String srId);

}
