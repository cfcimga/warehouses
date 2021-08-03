package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Dispatchlist;
import com.managementsystem.entity.Goodsreceivednote;
import com.managementsystem.entity.Stock;
import com.managementsystem.entity.StockAndDispatchlist;
import com.managementsystem.service.DispatchlistService;
import com.managementsystem.service.GoodsreceivednoteService;
import com.managementsystem.service.StockService;
import com.managementsystem.service.StoragerackService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/goodsreceivednote")
public class GoodsreceivednoteController {

    @Resource
    private GoodsreceivednoteService goodsreceivednoteService;

    @Resource
    private StoragerackService storagerackService;

    @Resource
    private StockService stockService;

    @Resource
    private DispatchlistService dispatchlistService;

    @GetMapping("/findAllGoodsReceivedNote")
    public Result findAllGoodsReceivedNote(){
        List<Goodsreceivednote> result = goodsreceivednoteService.list();
        if(result.size() >0){
            return Result.success(result);
        }else{
            return Result.fail("没有查到任何记录");
        }
    }

    @PostMapping("/addGoodsReceivedNote")
    public Result addGoodsReceivedNote(@RequestBody Goodsreceivednote goodsreceivednote,String dpId){
        int r=0;
        int result=0;
        try{
            result = goodsreceivednoteService.addGoodsreceivednote(goodsreceivednote);
            if(dpId!=null){
                r=10;
                r =  dispatchlistService.updateStates(5,dpId);
            }
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        if (result == 0){
            return Result.fail(400,"无法添加入库表，入库失败",result);
        }else if(r==10) {
            return Result.fail(400,"无法更新发货表状态，入库失败",r);
        }else{
            return Result.success(result);
        }
    }
    @PostMapping("/editGoodsReceivedNote")
    public Result editGoodsReceivedNote(@RequestBody Goodsreceivednote goodsreceivednote,String dpId,Integer dpState){ //(required=false)
//        return Result.success(carrier);
        int r=0;
        int result=5;
        try{
            result = result = goodsreceivednoteService.editGoodsreceivednote(goodsreceivednote);
            if(dpId!=null){
                r = 10;
                r =  dispatchlistService.updateStates(dpState,dpId);
            }
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        if (result == 0){
            return Result.fail(400,"无法添加入库表， 入库失败",result);
        }else if(r==10) {
            return Result.fail(400,"无法更新发货表状态，入库失败",r);
        }else{
            return Result.success(result);
        }
    }

    @DeleteMapping("/deleteGoodsReceivedNote")
    public Result deleteGoodsReceivedNote(String grnId,String dpId){
        int result=0;
        int r = 0;
        try{
            result = goodsreceivednoteService.deleteGoodsreceivednote(grnId);
            if(dpId!=null){
                r=10;
                r =  dispatchlistService.updateStates(4,dpId);
            }
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        if (result == 0){
            return Result.fail(400,"无法删除入库数据，删除失败",result);
        }else if(r==10) {
            return Result.fail(400,"无法更新发货表状态，删除失败",r);
        }else{
            return Result.success(result);
        }
    }

    @GetMapping("/updateState")
    public Result updateState(int grnState,String grnId){
        int result = goodsreceivednoteService.updateState(grnState,grnId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",result);
        }
    }

    @RequestMapping("/updateStockAndStoragerack")
    @Transactional
    public Result updateStockAndStoragerack(int grnState,String grnId,String dpId,@RequestBody Stock stock){ //删除库存与清空货架物料
        int result=0;
        int res=0;
        int re=0;
        int r = 0;
        try{
            result = stockService.addStock(stock);
            res = storagerackService.updateMaterial(stock.getMtId(),stock.getMtName(),stock.getSrId());
            System.out.println("物料："+stock.getMtId()+"\n"+"物料名："+stock.getMtName()+"\n"+"货架："+stock.getSrId());
            re = goodsreceivednoteService.updateState(grnState,grnId);
            if(dpId!=null){
                r=10;
                r =  dispatchlistService.updateStates(6,dpId);
            }
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        if (result == 0){
            return Result.fail(400,"无法添加库存明细，入库失败",result);
        }else if(res==0){
            return Result.fail(400,"无法执行更新货架物料信息，入库失败",res);
        }else if(re == 0){
            return Result.fail(400,"无法更新入库状态，入库失败",re);
        }else if(r==10) {
            return Result.fail(400,"无法更新发货表状态，入库失败",r);
        }else{
            return Result.success(result);
        }
    }
    @RequestMapping("/updateStock")
    @Transactional
    public Result updateStock(int grnState, String grnId,String dpId, @RequestBody Stock stock){ //修改库存与更新入库单状态   ,@RequestParam Dispatchlist dispatchlist
        int result=0;
        int re=0;
        int r = 0;
        try{

            result = stockService.editStock(stock);
            re = goodsreceivednoteService.updateState(grnState,grnId);
            if(dpId!=null){
                r=10;
                r =  dispatchlistService.updateStates(6,dpId);
            }
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        if (result == 0){
            return Result.fail(400,"无法更改库存，入库失败",result);
        }else if(re == 0){
            return Result.fail(400,"无法更新出库表状态，入库失败",re);
        }else if(r==10) {
            return Result.fail(400,"无法更新发货表状态，入库失败",re);
        }
        else{
            return Result.success(result);
        }
    }

    @GetMapping("/getGrnBySplId")
    public Result getGrnBySplId(String grnSplId){
        List<Goodsreceivednote> result = goodsreceivednoteService.getGrnBySplId(grnSplId);
        if (result.size() != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",result);
        }
    }

}
