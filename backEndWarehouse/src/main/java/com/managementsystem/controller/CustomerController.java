package com.managementsystem.controller;


import com.managementsystem.config.lang.Result;
import com.managementsystem.entity.Customer;
import com.managementsystem.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.managementsystem.config.lang.Result.fail;
import static com.managementsystem.config.lang.Result.success;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @GetMapping("/findAllCustomer")
    public Result findAllCustomer(){
        List<Customer> result = customerService.list();
        if(result.size()>=1){
            return success(result);
        }
        else{
            return fail("没有查询到数据");
        }
    }

    @PostMapping("/addCustomer")
    public Result addCustomer(@RequestBody Customer customer){
        int result = customerService.addCustomer(customer);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail("添加失败");
        }
    }
    @PostMapping("/editCustomer")
    public Result editCustomer(@RequestBody Customer customer){ //(required=false)
//        return Result.success(carrier);
        int result = customerService.editCustomer(customer);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",customer);
        }
    }

    @DeleteMapping("/deleteCustomer/{ctmId}")
    public Result deleteCustomer(@PathVariable("ctmId") String ctmId){
        int result = customerService.deleteCustomer(ctmId);
        if (result != 0){
            return Result.success(result);
        }else{
            return Result.fail(400,"修改失败",ctmId);
        }
    }

}
