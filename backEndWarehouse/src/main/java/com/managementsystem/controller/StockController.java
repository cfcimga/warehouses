package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Stock;
import com.managementsystem.service.StockService;
import com.managementsystem.service.StoragerackService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/stock")
public class StockController {

    @Resource
    private StockService stockService;



    @GetMapping("/findAllStock")
    public Result findAllStock(){
        List<Stock> result = stockService.list();
        if(result.size()>=1){
            System.out.println(result);
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }

    @PostMapping("/addStock")
    public Result addStock(@RequestBody Stock stock){
        int result = stockService.addStock(stock);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editStock")
    public Result editStock(@RequestBody Stock stock){ //(required=false)
//        return Result.success(carrier);
        int result = stockService.editStock(stock);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",stock);
        }
    }

    @DeleteMapping("/deleteStock")
//    public Result deleteStock(@PathVariable("srId") String srId,@PathVariable("mtId") String mtId){
    public Result deleteStock(String srId,String mtId){
        int result = stockService.deleteStock(srId,mtId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",srId);
        }
    }

    @GetMapping("/getStockBySrId")
    public Result getStockBySrId(String srId){
        List<Stock> result = stockService.getStockBySrId(srId);
        if(result.size()>=1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }

    @GetMapping("/getStockByMtId")
    public Result getStockByMtId(String mtId){
        List<Stock> result = stockService.getStockByMtId(mtId);
        if(result.size()>=1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }

//    @PostMapping("/updateCurrentStockByMtId")
//    public Result updateCurrentStockByMtId(String mtId,int number){ //(required=false)
////        return Result.success(carrier);
//        int result = stockService.editStock(stock);
//        if (result != 0){
//            return Result.success(result);
//        }else{
//            return Result.fail(400,"修改失败",stock);
//        }
//    }



}
