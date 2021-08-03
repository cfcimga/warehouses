package com.managementsystem.service.impl;

import com.managementsystem.entity.Reservoirarea;
import com.managementsystem.mapper.ReservoirareaMapper;
import com.managementsystem.service.ReservoirareaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ReservoirareaServiceImpl extends ServiceImpl<ReservoirareaMapper, Reservoirarea> implements ReservoirareaService {

    @Resource
    private ReservoirareaMapper reservoirareaMapper;
    @Override
    public List<Reservoirarea> list(){
        return reservoirareaMapper.findAll();
    }

    @Override
    public int addReservoirarea(Reservoirarea reservoirarea){
        return reservoirareaMapper.addReservoirarea(reservoirarea);
    }
    @Override
    public int editReservoirarea(Reservoirarea reservoirarea){
        return reservoirareaMapper.editReservoirarea(reservoirarea);
    }
    @Override
    public int deleteReservoirarea(String raId){
        return reservoirareaMapper.deleteReservoirarea(raId);
    }
    @Override
    public List<Reservoirarea> getReservoirareaByWhId(String whId){
        return reservoirareaMapper.getReservoirareaByWhId(whId);
    }

}
