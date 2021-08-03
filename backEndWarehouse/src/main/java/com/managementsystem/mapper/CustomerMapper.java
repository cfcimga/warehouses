package com.managementsystem.mapper;

import com.managementsystem.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CustomerMapper extends BaseMapper<Customer> {

    List<Customer> findAll();


    Customer findCreator();

    int addCustomer(Customer customer);

    int editCustomer(Customer customer);

    int deleteCustomer(@Param("ctmId")String ctmId);
}
