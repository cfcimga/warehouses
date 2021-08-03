package com.managementsystem.mapper;

import com.managementsystem.entity.Mdform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface MdformMapper extends BaseMapper<Mdform> {

    int addMdForm(Mdform mdform);

}
