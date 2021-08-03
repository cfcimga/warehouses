package com.managementsystem.service.impl;

import com.managementsystem.entity.Storagerack;
import com.managementsystem.mapper.StoragerackMapper;
import com.managementsystem.service.StoragerackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class StoragerackServiceImpl extends ServiceImpl<StoragerackMapper, Storagerack> implements StoragerackService {

    @Resource
    private StoragerackMapper storagerackMapper;
    @Override
    public List<Storagerack> list(){
        return storagerackMapper.findAll();
    }

    @Override
    public int addStoragerack(Storagerack storagerack){
        return storagerackMapper.addStoragerack(storagerack);
    }
    @Override
    public int editStoragerack(Storagerack storagerack){
        return storagerackMapper.editStoragerack(storagerack);
    }
    @Override
    public int deleteStoragerack(String srId){
        return storagerackMapper.deleteStoragerack(srId);
    }
    @Override
    public List<Storagerack> getStoragerackBySrRaId(String raId){
        return storagerackMapper.getStoragerackBySrRaId(raId);
    }
    @Override
    public int updateMaterial(String mtId,String mtName, String srId){
        return storagerackMapper.updateMaterial(mtId,mtName,srId);
    }
    @Override
    public int clearMaterial(String srId){
        return storagerackMapper.clearMaterial(srId);
    }
}
