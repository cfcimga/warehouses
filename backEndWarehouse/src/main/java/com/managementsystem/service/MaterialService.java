package com.managementsystem.service;

import com.managementsystem.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import com.managementsystem.entity.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MaterialService extends IService<Material> {

    List<Material> list();
    Material findMaterialById(String id , String mtSrId);

    Material findMaterialByOne(String id);

    int addMaterial(Material material);

    int editMaterial(Material material);

    int deleteMaterial(String mtId);
}
