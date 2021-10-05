package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private ICustomerService iCustomerService;
//    @GetMapping("/")
//    public String test1(){
//        return "testt";
//    }

    @GetMapping("/list")
    public List<Customer> getAllCustomer(){
        return iCustomerService.getAllCustomer();
    }
}
