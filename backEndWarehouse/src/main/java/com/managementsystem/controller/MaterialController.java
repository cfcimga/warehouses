package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;

import com.managementsystem.entity.Material;
import com.managementsystem.service.MaterialService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/material")
public class MaterialController {
    @Resource
    private MaterialService materialService;

    @GetMapping("/findAllMaterial")
    public Result findAllGoodsReceivedNote(){
        List<Material> result = materialService.list();
        if(result.size()>=1){
            return Result.success(result);
        }else{
            return Result.fail("没有查到任何记录");
        }
    }

    @GetMapping("/findMaterialById")
    public Result findMaterialById(@RequestParam("mtId") String mtId,@RequestParam("mtSrId") String mtSrId){

//        return Result.success(mtId+mtSrId);
        Material result = materialService.findMaterialById(mtId,mtSrId);
        if(result != null){
            return Result.success(result);
        }else{
            return Result.fail("没有查到任何记录");
        }
    }

    @GetMapping("/findMaterialByOne")
    public Result findMaterialByOne(String mtId){
        Material result = materialService.findMaterialByOne(mtId);
        if(result != null){
            return Result.success(result);
        }else{
            return Result.fail("没有查到任何记录");
        }
    }

    @PostMapping("/addMaterial")
    public Result addMaterial(@RequestBody Material material){
        int result = materialService.addMaterial(material);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editMaterial")
    public Result editMaterial(@RequestBody Material material){ //(required=false)
//        return Result.success(carrier);
        int result = materialService.editMaterial(material);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",material);
        }
    }

    @DeleteMapping("/deleteMaterial/{mtId}")
    public Result deleteMaterial(@PathVariable("mtId") String mtId){
        int result = materialService.deleteMaterial(mtId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",mtId);
        }
    }

}
