package com.managementsystem.service.impl;

import com.managementsystem.entity.Dispatchlist;
import com.managementsystem.mapper.DispatchlistMapper;
import com.managementsystem.service.DispatchlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class DispatchlistServiceImpl extends ServiceImpl<DispatchlistMapper, Dispatchlist> implements DispatchlistService {

    @Resource
    private DispatchlistMapper dispatchlistMapper;

    @Override
    public List<Dispatchlist> list(){
        return dispatchlistMapper.findAll();
    }

    @Override
    public int addDispatchlist(Dispatchlist dispatchlist){
        return dispatchlistMapper.addDispatchlist(dispatchlist);
    }
    @Override
    public int editDispatchlist(Dispatchlist dispatchlist){
        return dispatchlistMapper.editDispatchlist(dispatchlist);
    }
    @Override
    public int deleteDispatchlist(String dpId){
        return dispatchlistMapper.deleteDispatchlist(dpId);
    }

    @Override
    public int updateStates(int dpState,String dpId){
        return dispatchlistMapper.updateState(dpState,dpId);
    }

    @Override
    public List<Dispatchlist> findReturnNote(int dpState){
        return dispatchlistMapper.findReturnNote(dpState);
    }

    @Override
    public List<Dispatchlist> getDpByCIdAndState(String dpCId){
        return dispatchlistMapper.getDpByCIdAndState(dpCId);
    }


}
