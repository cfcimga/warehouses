package com.managementsystem.service;

import com.managementsystem.entity.Inform;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface InformService extends IService<Inform> {

    List<Inform> findAll();

    int addInform(Inform inform);

    int deleteInform(String fId);

}
