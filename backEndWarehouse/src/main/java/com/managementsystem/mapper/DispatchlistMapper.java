package com.managementsystem.mapper;

import com.managementsystem.entity.Dispatchlist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DispatchlistMapper extends BaseMapper<Dispatchlist> {
    List<Dispatchlist> findAll();

    int addDispatchlist(Dispatchlist dispatchlist);

    int editDispatchlist(Dispatchlist dispatchlist);


    int deleteDispatchlist(@Param("dpId")String dpId);

    int updateState(@Param("dpState")int dpState,@Param("dpId")String dpId);

    List<Dispatchlist> findReturnNote(@Param("dpState") int dpState);

    List<Dispatchlist> getDpByCIdAndState(@Param("dpCId") String dpCId);


//    List<Dispatchlist> getDispatchlistBySrId(@Param("srId") String srId);
}
