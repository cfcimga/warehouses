package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Storagerack;
import com.managementsystem.service.StoragerackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/storagerack")
public class StoragerackController {

    @Resource
    private StoragerackService storagerackService;

    @GetMapping("/findAllStoragerack")
    public Result findAllStoragerack() {
        List<Storagerack> result = storagerackService.list();
        if (result.size()!=0) {
            return Result.success(result);
        } else {
            return Result.fail("没有查询到任何记录");
        }
    }

    @PostMapping("/addStoragerack")
    public Result addStoragerack(@RequestBody Storagerack storagerack) {
        int result = storagerackService.addStoragerack(storagerack);
        if (result != 0) {
            return Result.success(result);
        } else {
            return Result.fail("添加失败");
        }
    }

    @PostMapping("/editStoragerack")
    public Result editStoragerack(@RequestBody Storagerack storagerack) { //(required=false)
//        return Result.success(carrier);
        int result = storagerackService.editStoragerack(storagerack);
        if (result != 0) {
            return Result.success(result);
        } else {
            return Result.fail(400, "修改失败", storagerack);
        }
    }

    @DeleteMapping("/deleteStoragerack/{srId}")
    public Result deleteStoragerack(@PathVariable("srId") String srId) {
        int result = storagerackService.deleteStoragerack(srId);
        if (result != 0) {
            return Result.success(result);
        } else {
            return Result.fail(400, "修改失败", srId);
        }
    }

    @GetMapping("/getStoragerackBySrRaId")
    public Result getStoragerackBySrRaId(String raId) {
        List<Storagerack> result = storagerackService.getStoragerackBySrRaId(raId);
        if (result.size() >= 1) {
            return Result.success(result);
        } else {
            return Result.fail("没有查询到任何记录");
        }
    }

    @GetMapping("/updateMaterial")
    public Result updateMaterial(String mtId, String mtName, String srId) {
        //        return Result.success(carrier);
        System.out.println("nihao:"+mtId+mtName+srId);
        int result = storagerackService.updateMaterial(mtId,mtName,srId);
        if (result != 0) {
            return Result.success(result);
        } else {
            return Result.fail(400, "修改失败", result);
        }
    }
    @GetMapping("/clearMaterial")
    public Result clearMaterial(String srId) {
        //        return Result.success(carrier);
        int result = storagerackService.clearMaterial(srId);
        if (result != 0) {
            return Result.success(result);
        } else {
            return Result.fail(400, "修改失败", result);
        }
    }

}
