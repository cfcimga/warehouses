package com.managementsystem.service.impl;

import com.managementsystem.entity.Outdeliverlist;
import com.managementsystem.mapper.OutdeliverlistMapper;
import com.managementsystem.service.OutdeliverlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class OutdeliverlistServiceImpl extends ServiceImpl<OutdeliverlistMapper, Outdeliverlist> implements OutdeliverlistService {

    @Resource
    private OutdeliverlistMapper outdeliverlistMapper;
    @Override
    public List<Outdeliverlist> list(){
        return outdeliverlistMapper.findAll();
    }

    @Override
    public int addOutdeliverlist(Outdeliverlist outdeliverlist){
        return outdeliverlistMapper.addOutdeliverlist(outdeliverlist);
    }

    @Override
    public int editOutdeliverlist(Outdeliverlist outdeliverlist){
        return outdeliverlistMapper.editOutdeliverlist(outdeliverlist);
    }

    @Override
    public int deleteOutdeliverlist(String odlId){
        return outdeliverlistMapper.deleteOutdeliverlist(odlId);
    }

    @Override
    public int updateOdlState(int odlState,String odlId){
        return outdeliverlistMapper.updateState(odlState,odlId);
    }


    @Override
    public List<Outdeliverlist> getOdlByCtmId(String odlCustomerId){
        return outdeliverlistMapper.getOdlByCtmId(odlCustomerId);
    }

}
