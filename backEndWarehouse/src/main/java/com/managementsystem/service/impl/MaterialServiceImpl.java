package com.managementsystem.service.impl;

import com.managementsystem.entity.Material;
import com.managementsystem.mapper.MaterialMapper;
import com.managementsystem.service.MaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

    @Resource
    private MaterialMapper materialMapper;
    @Override
    public List<Material> list(){
        return materialMapper.findAll();
    }
    @Override
    public Material findMaterialById(String id,String mtSrId){
        return materialMapper.findMaterialById(id,mtSrId);
    }
    @Override
    public Material findMaterialByOne(String id){
        return materialMapper.findMaterialByOne(id);
    }

    @Override
    public int addMaterial(Material material){
        return materialMapper.addMaterial(material);
    }
    @Override
    public int editMaterial(Material material){
        return materialMapper.editMaterial(material);
    }
    @Override
    public int deleteMaterial(String mtId){
        return materialMapper.deleteMaterial(mtId);
    }
}
