package com.managementsystem.mapper;

import com.managementsystem.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StockMapper extends BaseMapper<Stock> {
    List<Stock> findAll();

    int addStock(Stock stock);

    int editStock(Stock stock);

    int deleteStock(@Param("srId")String srId,@Param("mtId")String mtId);

    List<Stock> getStockBySrId(@Param("srId") String srId);

    List<Stock> getStockByMtId(@Param("mtId") String mtId);






//    int updateStock(@Param("srId")String srId,@Param("mtId")String mtId,@Param("currentStock") String currentStock);

}
