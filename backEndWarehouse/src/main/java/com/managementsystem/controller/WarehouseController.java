package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Warehouse;
import com.managementsystem.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;

    @GetMapping("/findAllWarehouse")
    public Result findAllWarehouse(){
        List<Warehouse> result = warehouseService.list();
        if (result.size() != 0){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }

    @PostMapping("/addWarehouse")
    public Result addWarehouse(@RequestBody Warehouse warehouse){
        int result = warehouseService.addWarehouse(warehouse);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editWarehouse")
    public Result editWarehouse(@RequestBody Warehouse warehouse){ //(required=false)
//        return Result.success(carrier);
        int result = warehouseService.editWarehouse(warehouse);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",warehouse);
        }
    }

    @DeleteMapping("/deleteWarehouse/{whId}")
    public Result deleteWarehouse(@PathVariable("whId") String whId){
        int result = warehouseService.deleteWarehouse(whId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",whId);
        }
    }
    @GetMapping("/getWarehouseById")
    public Result getWarehouseById(String whId){
        Warehouse result = warehouseService.getWarehouseById(whId);
        if (result != null){
            return Result.success(result);
        }else{
            return Result.fail(400,"查询失败",whId);
        }
    }


}
