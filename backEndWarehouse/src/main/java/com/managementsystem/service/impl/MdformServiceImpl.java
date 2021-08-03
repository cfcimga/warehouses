package com.managementsystem.service.impl;

import com.managementsystem.entity.Mdform;
import com.managementsystem.mapper.MdformMapper;
import com.managementsystem.service.MdformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MdformServiceImpl extends ServiceImpl<MdformMapper, Mdform> implements MdformService {

    @Resource
    private MdformMapper mdformMapper;

    @Override
    public int addMdform(Mdform mdform){
        return mdformMapper.addMdForm(mdform);
    }

}
