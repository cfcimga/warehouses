package com.managementsystem.mapper;

import com.managementsystem.entity.Inform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface InformMapper extends BaseMapper<Inform> {
    List<Inform> findAll();

    int addInform(Inform inform);

    int deleteInform(@Param("fId") String fId);

}
