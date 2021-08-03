package com.managementsystem.service;

import com.managementsystem.entity.Stock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface StockService extends IService<Stock> {

    List<Stock> list();

    int addStock(Stock stock);

    int editStock(Stock stock);

    int deleteStock(String srId,String mtId);

    List<Stock> getStockBySrId(String srId);

    List<Stock> getStockByMtId(String mtId);

}
