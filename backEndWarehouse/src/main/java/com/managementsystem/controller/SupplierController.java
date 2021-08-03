package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Supplier;
import com.managementsystem.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Resource
    private SupplierService supplierService;

    @GetMapping("/findAllSupplier")
    public Result findAllSupplier(){
        List<Supplier> result = supplierService.list();
        if (result.size() != 0){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何记录");
        }
    }

    @PostMapping("/addSupplier")
    public Result addSupplier(@RequestBody Supplier supplier){
        int result = supplierService.addSupplier(supplier);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editSupplier")
    public Result editSupplier(@RequestBody Supplier supplier){ //(required=false)
//        return Result.success(carrier);
        int result = supplierService.editSupplier(supplier);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",supplier);
        }
    }

    @DeleteMapping("/deleteSupplier/{splId}")
    public Result deleteSupplier(@PathVariable("splId") String splId){
        int result = supplierService.deleteSupplier(splId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",splId);
        }
    }

}
