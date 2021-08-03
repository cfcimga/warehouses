package com.managementsystem.service.impl;

import com.managementsystem.entity.Goodsreceivednote;
import com.managementsystem.mapper.GoodsreceivednoteMapper;
import com.managementsystem.service.GoodsreceivednoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class GoodsreceivednoteServiceImpl extends ServiceImpl<GoodsreceivednoteMapper, Goodsreceivednote> implements GoodsreceivednoteService {

    @Resource
    private GoodsreceivednoteMapper goodsreceivednoteMapper;

    @Override
    public List<Goodsreceivednote> list(){
        return goodsreceivednoteMapper.findAll();
    }

    @Override
    public int addGoodsreceivednote(Goodsreceivednote goodsreceivednote){
        return goodsreceivednoteMapper.addGoodsreceivednote(goodsreceivednote);
    }

    @Override
    public int editGoodsreceivednote(Goodsreceivednote goodsreceivednote){
        return goodsreceivednoteMapper.editGoodsreceivednote(goodsreceivednote);
    }

    @Override
    public int deleteGoodsreceivednote(String grnId){
        return goodsreceivednoteMapper.deleteGoodsreceivednote(grnId);
    }

    @Override
    public int updateState(int grnState,String grnId){
        return goodsreceivednoteMapper.updateState(grnState,grnId);
    }

    @Override
    public List<Goodsreceivednote> getGrnBySplId(String grnSplId){
        return goodsreceivednoteMapper.getGrnBySplId(grnSplId);
    }
}
