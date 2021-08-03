package com.managementsystem.service;

import com.managementsystem.entity.Outdeliverlist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface OutdeliverlistService extends IService<Outdeliverlist> {

    List<Outdeliverlist> list();

    int addOutdeliverlist(Outdeliverlist outdeliverlist);

    int editOutdeliverlist(Outdeliverlist outdeliverlist);

    int deleteOutdeliverlist(String odlId);

    int updateOdlState(int odlState,String odlId);

    List<Outdeliverlist> getOdlByCtmId(String odlCustomerId);
}
