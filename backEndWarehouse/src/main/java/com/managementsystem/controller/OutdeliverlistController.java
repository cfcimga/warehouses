package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Dispatchlist;
import com.managementsystem.entity.Outdeliverlist;
import com.managementsystem.entity.Stock;
import com.managementsystem.service.DispatchlistService;
import com.managementsystem.service.OutdeliverlistService;
import com.managementsystem.service.StockService;
import com.managementsystem.service.StoragerackService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/outdeliverlist")
public class OutdeliverlistController {

    @Resource
    private OutdeliverlistService outdeliverlistService;

    @Resource
    private StoragerackService storagerackService;

    @Resource
    private StockService stockService;

    @Resource
    private DispatchlistService dispatchlistService;

    @GetMapping("/findAllOutdeliverlist")
    public Result findAllOutdeliverlist(){
        List<Outdeliverlist> result = outdeliverlistService.list();
        if(result.size()>0){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }

    @PostMapping("/addOutdeliverlist")
    public Result addOutdeliverlist(@RequestBody Outdeliverlist outdeliverlist){
        int result = outdeliverlistService.addOutdeliverlist(outdeliverlist);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editOutdeliverlist")
    public Result editOutdeliverlist(@RequestBody Outdeliverlist outdeliverlist){ //(required=false)
//        return Result.success(carrier);
        int result = outdeliverlistService.editOutdeliverlist(outdeliverlist);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",outdeliverlist);
        }
    }

    @DeleteMapping("/deleteOutdeliverlist/{odlId}")
    public Result deleteOutdeliverlist(@PathVariable("odlId") String odlId){
        int result = outdeliverlistService.deleteOutdeliverlist(odlId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",odlId);
        }
    }
    @GetMapping("/updateOdlState")
    public Result updateOdlState(int odlState,String odlId){
        int result = outdeliverlistService.updateOdlState(odlState,odlId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",result);
        }
    }

    @RequestMapping("/deleteStockAndStoragerack")
    @Transactional
    public Result deleteStockAndStoragerack(String srId,String mtId,int odlState,String odlId,@RequestBody Dispatchlist dispatchlist){ //删除库存与清空货架物料
        int result=0;
        int res=0;
        int re=0;
        int r = 0;
        try{
            result = stockService.deleteStock(srId,mtId);
            res = storagerackService.updateMaterial(null,null,srId);
            re = outdeliverlistService.updateOdlState(odlState,odlId);
            r = dispatchlistService.addDispatchlist(dispatchlist);

        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        if (result == 0){
            return Result.fail(400,"无法删除库存明细，出库失败",result);
        }else if(res==0){
            return Result.fail(400,"无法执行清除货架物料信息，出库失败",res);
        }else if(re == 0){
            return Result.fail(400,"无法更新出库表状态，出库失败",re);
        }else if(r == 0){
            return Result.fail(400,"无法添加发货单，出库失败",r);
        }else{
            return Result.success(result);
        }
    }
    @RequestMapping("/reduceStock")
    @Transactional
    public Result reduceStock(int odlState, String odlId,@RequestBody Stock stock,String dpId, String dpCreationTime, int dpCreatorId){ //删除库存与清空货架物料   ,@RequestParam Dispatchlist dispatchlist
        int result=0;
        int re=0;
        int r = 0;
        try{
            Dispatchlist dispatchlist = new Dispatchlist();
            dispatchlist.setDpId(dpId);
            dispatchlist.setDpOdlId(odlId);
            dispatchlist.setDpCreationTime(dpCreationTime);
            dispatchlist.setDpCreatorId(dpCreatorId);
            dispatchlist.setDpState(1);
            result = stockService.editStock(stock);
            re = outdeliverlistService.updateOdlState(odlState,odlId);

            r = dispatchlistService.addDispatchlist(dispatchlist);
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        if (result == 0){
            return Result.fail(400,"无法更改库存，出库失败",result);
        }else if(re == 0){
            return Result.fail(400,"无法更新出库表状态，出库失败",re);
        }
        else if(r == 0){
            return Result.fail(400,"无法添加发货单，出库失败 ",r);
        }
        else{
            return Result.success(result);
        }
    }


    @GetMapping("/getOdlByCtmId")
    public Result getOdlByCtmId(String odlCustomerId){
        List<Outdeliverlist> result = outdeliverlistService.getOdlByCtmId(odlCustomerId);
        if (result.size() != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",result);
        }
    }



}
