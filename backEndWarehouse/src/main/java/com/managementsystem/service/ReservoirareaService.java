package com.managementsystem.service;

import com.managementsystem.entity.Reservoirarea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ReservoirareaService extends IService<Reservoirarea> {

    List<Reservoirarea> list();

    int addReservoirarea(Reservoirarea reservoirarea);

    int editReservoirarea(Reservoirarea reservoirarea);

    int deleteReservoirarea(String raId);

    List<Reservoirarea> getReservoirareaByWhId(String whId);

}
