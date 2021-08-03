package com.managementsystem.mapper;

import com.managementsystem.entity.Goodsreceivednote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GoodsreceivednoteMapper extends BaseMapper<Goodsreceivednote> {

    List<Goodsreceivednote> findAll();

    int addGoodsreceivednote(Goodsreceivednote goodsreceivednote);

    int editGoodsreceivednote(Goodsreceivednote goodsreceivednote);

    int deleteGoodsreceivednote(@Param("grnId")String grnId);

    int updateState(@Param("grnState") int grnState,@Param("grnId")String grnId);

    List<Goodsreceivednote> getGrnBySplId(@Param("grnSplId") String grnSplId);


//    int editSrById(@Param("grnSrId") String grnSrId,@Param("grnSrName") String grnSrName,@Param("grnId") String grnId);

}
