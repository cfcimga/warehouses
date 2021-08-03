package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Inform;
import com.managementsystem.service.InformService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/inform")
public class InformController {

    @Resource
    private InformService informService;

    @GetMapping("/findAllInform")
    public Result findAllInform(){
        List<Inform> result = informService.findAll();

        if (result.size()>=1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何数据");
        }
    }

    @PostMapping("/addInform")
    public Result addInform(@RequestBody Inform inform){
        System.out.println("inform:"+inform);
        int result = informService.addInform(inform);

        if (result!=0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }

    @DeleteMapping("/deleteInform")
    public Result deleteInform(String fId){
        System.out.println("fId:"+fId);
        int result = informService.deleteInform(fId);

        if (result!=0){
            return Result.success(result);
        }else{
            return Result.fail("删除失败！");
        }
    }


}
