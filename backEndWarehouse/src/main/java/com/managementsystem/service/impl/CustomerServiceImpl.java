package com.managementsystem.service.impl;

import com.managementsystem.entity.Customer;
import com.managementsystem.mapper.CustomerMapper;
import com.managementsystem.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> list(){
        return customerMapper.findAll();
    }

    @Override
    public int addCustomer(Customer customer){
        return customerMapper.addCustomer(customer);
    }

    public int editCustomer(Customer customer){
        return customerMapper.editCustomer(customer);
    }

    public int deleteCustomer(String ctmId){
        return customerMapper.deleteCustomer(ctmId);
    }
}
