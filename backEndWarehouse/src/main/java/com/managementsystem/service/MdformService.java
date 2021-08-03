package com.managementsystem.service;

import com.managementsystem.entity.Mdform;
import com.baomidou.mybatisplus.extension.service.IService;


public interface MdformService extends IService<Mdform> {

    int addMdform(Mdform mdform);

}
