package com.managementsystem.service.impl;

import com.managementsystem.entity.Stock;
import com.managementsystem.mapper.StockMapper;
import com.managementsystem.service.StockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {
    @Resource
    private StockMapper stockMapper;
    @Override
    public List<Stock> list(){
        return stockMapper.findAll();
    }

    @Override
    public int addStock(Stock stock){
        return stockMapper.addStock(stock);
    }
    @Override
    public int editStock(Stock stock){
        return stockMapper.editStock(stock);
    }
    @Override
    public int deleteStock(String srId,String mtId){
        return stockMapper.deleteStock(srId,mtId);
    }
    @Override
    public List<Stock> getStockBySrId(String srId){
        return stockMapper.getStockBySrId(srId);
    }

    @Override
    public List<Stock> getStockByMtId(String mtId){
        return stockMapper.getStockByMtId(mtId);
    }


}
