package com.managementsystem.mapper;

import com.managementsystem.entity.Outdeliverlist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OutdeliverlistMapper extends BaseMapper<Outdeliverlist> {

    List<Outdeliverlist> findAll();

    int addOutdeliverlist(Outdeliverlist outdeliverlist);

    int editOutdeliverlist(Outdeliverlist outdeliverlist);

    int deleteOutdeliverlist(@Param("odlId")String odlId);

    int updateState(@Param("odlState") int odlState,@Param("odlId")String odlId);

    List<Outdeliverlist> getOdlByCtmId(@Param("odlCustomerId") String odlCustomerId);

}
