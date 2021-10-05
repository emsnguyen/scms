package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;


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
    public ResponseEntity<List<Customer>> getAllCustomer(){
        log.info("[Start Customer - list]");
        List<Customer> customerslist = iCustomerService.getAllCustomer();
        log.info("[End Customer - list]");
        return status(HttpStatus.OK).body(customerslist);
    }

}
