package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Carrier;
import com.managementsystem.entity.User;
import com.managementsystem.service.CarrierService;
import com.managementsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/carrier")
public class CarrierController {

    @Resource
    private CarrierService carrierService;

    @GetMapping("/findAllCarrier")
    public Result findAllCarrier(@RequestHeader Map<String,Object> he){
        System.out.println("he:"+he);
        List<Carrier> result = carrierService.list();

        if (result.size()>=1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何数据");
        }
    }
    @GetMapping("/findCreator")
    public Result findCreator(){
        List<Carrier> result = carrierService.findCreator();
        if (result.size() >= 1){
            return Result.success(result);
        }else{
            return Result.fail("没有查询到任何数据");
        }
    }

    @PostMapping("/addCarrier")
    public Result addCarrier(@RequestBody Carrier carrier){
        int result = carrierService.addCarrier(carrier);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editCarrier")
    public Result editCarrier(@RequestBody Carrier carrier){ //(required=false)
//        return Result.success(carrier);
        int result = carrierService.editCarrier(carrier);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",carrier);
        }
    }

    @DeleteMapping("/deleteCarrier")
    @Transactional
    public Result deleteCarrier(String cId){

        int result=0;
        List<Carrier> s = null;
        try{
            result = carrierService.deleteCarrier(cId);
            s = carrierService.findCreator();
            System.out.println("carrier:"+s);
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = 0;
        }
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"删除失败",s);
        }
    }



}
