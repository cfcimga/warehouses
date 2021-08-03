package com.managementsystem.mapper;

import com.managementsystem.entity.Reservoirarea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.managementsystem.entity.Warehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ReservoirareaMapper extends BaseMapper<Reservoirarea> {

    List<Reservoirarea> findAll();

    int addReservoirarea(Reservoirarea reservoirarea);

    int editReservoirarea(Reservoirarea reservoirarea);

    int deleteReservoirarea(@Param("raId")String raId);

    List<Reservoirarea> getReservoirareaByWhId(@Param("whId") String whId);

}
