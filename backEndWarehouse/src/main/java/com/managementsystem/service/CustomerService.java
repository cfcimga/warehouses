package com.managementsystem.service;

import com.managementsystem.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface CustomerService extends IService<Customer> {


    List<Customer> list();

    int addCustomer(Customer customer);

    int editCustomer(Customer customer);

    int deleteCustomer(String ctmId);

}
