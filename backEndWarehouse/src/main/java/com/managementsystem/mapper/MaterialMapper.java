package com.managementsystem.mapper;

import com.managementsystem.entity.Material;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MaterialMapper extends BaseMapper<Material> {

    List<Material> findAll();

    Material findMaterialByOne(String mtId);

    Material findMaterialById(@Param("mtId") String mtId, @Param("mtSrId") String mtSrId);

    int addMaterial(Material material);

    int editMaterial(Material material);

    int deleteMaterial(@Param("mtId")String mtId);

}
