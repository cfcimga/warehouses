package com.managementsystem.service.impl;

import com.managementsystem.entity.Inform;
import com.managementsystem.mapper.InformMapper;
import com.managementsystem.service.InformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class InformServiceImpl extends ServiceImpl<InformMapper, Inform> implements InformService {

    @Resource
    private InformMapper informMapper;

    @Override
    public List<Inform> findAll(){
        return informMapper.findAll();
    }

    @Override
    public int addInform(Inform inform){
        return informMapper.addInform(inform);
    }


    @Override
    public int deleteInform(String fId){
        return informMapper.deleteInform(fId);
    }

}
