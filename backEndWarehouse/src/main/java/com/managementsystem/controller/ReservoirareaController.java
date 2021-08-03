package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Reservoirarea;
import com.managementsystem.service.ReservoirareaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/reservoirarea")
public class ReservoirareaController {

    @Resource
    private ReservoirareaService reservoirareaService;

    @GetMapping("/findAllReservoirarea")
    public Result findAllReservoirarea(){
        List<Reservoirarea> result = reservoirareaService.list();
        if(result.size() >= 1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }

    @PostMapping("/addReservoirarea")
    public Result addReservoirarea(@RequestBody Reservoirarea reservoirarea){
        int result = reservoirareaService.addReservoirarea(reservoirarea);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editReservoirarea")
    public Result editReservoirarea(@RequestBody Reservoirarea reservoirarea){ //(required=false)
//        return Result.success(carrier);
        int result = reservoirareaService.editReservoirarea(reservoirarea);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",reservoirarea);
        }
    }

    @DeleteMapping("/deleteReservoirarea/{raId}")
    public Result deleteReservoirarea(@PathVariable("raId") String raId){
        int result = reservoirareaService.deleteReservoirarea(raId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",raId);
        }
    }

    @GetMapping("/getReservoirareaByWhId")
    public Result getReservoirareaByWhId(String whId){
        List<Reservoirarea> result = reservoirareaService.getReservoirareaByWhId(whId);
        if(result.size() >= 1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }
}
