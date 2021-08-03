package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Mdform;
import com.managementsystem.service.MdformService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/mdform")
public class MdformController {
    @Resource
    private MdformService mdformService;

    @PostMapping("/addMdform")
    public Result addMdform(@RequestBody Mdform mdform){
        System.out.println("mdform:"+mdform);
        int result = mdformService.addMdform(mdform);

        if (result!=0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }

}
