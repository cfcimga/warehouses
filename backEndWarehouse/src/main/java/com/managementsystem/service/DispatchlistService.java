package com.managementsystem.service;

import com.managementsystem.entity.Dispatchlist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface DispatchlistService extends IService<Dispatchlist> {
    List<Dispatchlist> list();

    int addDispatchlist(Dispatchlist dispatchlist);

    int editDispatchlist(Dispatchlist dispatchlist);

    int deleteDispatchlist(String dpId);

    int updateStates(int dpState,String dpId);

    List<Dispatchlist> findReturnNote(int dpState);

    List<Dispatchlist> getDpByCIdAndState(String dpCId);

}
