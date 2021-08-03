package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Dispatchlist;
import com.managementsystem.service.DispatchlistService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/dispatchlist")
public class DispatchlistController {

    @Resource
    private DispatchlistService dispatchlistService;

    @GetMapping("/findAllDispatchlist")
    public Result findAllDispatchlist(){
        List<Dispatchlist> result = dispatchlistService.list();
        System.out.println("result:"+result);
        if(result.size()>=1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到数据");
        }
    }

    @PostMapping("/addDispatchlist")
    public Result addDispatchlist(@RequestBody Dispatchlist dispatchlist){
        int result = dispatchlistService.addDispatchlist(dispatchlist);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editDispatchlist")
    public Result editDispatchlist(@RequestBody Dispatchlist dispatchlist){ //(required=false)
        System.out.println("测试："+dispatchlist);
        int result = dispatchlistService.editDispatchlist(dispatchlist);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",dispatchlist);
        }
    }

    @DeleteMapping("/deleteDispatchlist")
    public Result deleteDispatchlist(String dpId){
        int result = dispatchlistService.deleteDispatchlist(dpId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",dpId);
        }
    }

    @PostMapping("/updateState")
    public Result updateState(int dpState,String dpId){ //(required=false)
        int result = dispatchlistService.updateStates(dpState,dpId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",result);
        }
    }
    @GetMapping("findReturnNote")
    public Result findReturnNote(int dpState){
        List<Dispatchlist> result = dispatchlistService.findReturnNote(dpState);
        if(result.size()>=1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到数据");
        }
    }
    @GetMapping("/getDpByCIdAndState")
    public Result getDpByCIdAndState(String dpCId){
        List<Dispatchlist> result = dispatchlistService.getDpByCIdAndState(dpCId);
        if (result.size() != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",result);
        }
    }
}
