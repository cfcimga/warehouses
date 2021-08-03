package com.managementsystem.service;

import com.managementsystem.entity.Goodsreceivednote;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface GoodsreceivednoteService extends IService<Goodsreceivednote> {

    List<Goodsreceivednote> list();


    int addGoodsreceivednote(Goodsreceivednote goodsreceivednote);

    int editGoodsreceivednote(Goodsreceivednote goodsreceivednote);

    int deleteGoodsreceivednote(String grnId);

    int updateState(int grnState,String grnId);


    List<Goodsreceivednote> getGrnBySplId(String grnSplId);
}
