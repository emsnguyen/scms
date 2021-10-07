package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.repository.CustomerRepository;
import com.scms.supplychainmanagementsystem.repository.UserRepository;
import com.scms.supplychainmanagementsystem.service.ICustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private ICustomerService iCustomerService;


    @GetMapping("/list")
    public ResponseEntity<List<CustomerDto>> getAllCustomer(Pageable page){
        log.info("[Start Customer - list]");
        Page<Customer> customerslist = iCustomerService.getAllCustomerInWarehouse(page);
        log.info("[End Customer - list]");
        List<Customer> listcus = customerslist.getContent();
        List<CustomerDto> listDto = new ArrayList<>();
        for(int i=0;i<listcus.size();i++){
            CustomerDto cuss = new CustomerDto(listcus.get(i));
            listDto.add(cuss);
        }
        return status(HttpStatus.OK).body(listDto);
    }


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        log.info("[Start CustomerController -  createCustomer " + customerDto.getEmail() + "]");
        iCustomerService.saveCustomer(customerDto);
        log.info("[End CustomerController -  createCustomer " + customerDto.getEmail()+ "]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{customerid}")
    public ResponseEntity<CustomerDto> getCustomerByIdInWareHouse(@PathVariable Long customerid) {
        log.info("[Start CustomerController - Get Customer By ID]");
        Customer customer = iCustomerService.getCustomerByIdInWarehouse(customerid);
        CustomerDto customerDto = new CustomerDto(customer);
        log.info("[End CustomerController - Get Customer By ID]");
        return status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerDto customerDto) {
        log.info("[Start CustomerController - Update Customer with email " + customerDto.getEmail() + "]");
        iCustomerService.updateCustomer(customerId,customerDto);
        log.info("[End CustomerController - Update Customer with email " + customerDto.getEmail() + "]");
        return new ResponseEntity<>("Update Customer Successfully", OK);
    }

    @DeleteMapping ("/delete/{customerid}")
    public ResponseEntity<String> DeleteCustomer(@PathVariable Long customerid) {
        log.info("[Start CustomerController - Get Customer By ID]");
         iCustomerService.deleteCustomer(customerid);
        log.info("[End CustomerController - Get Customer By ID]");
        return new ResponseEntity<>("Delete Customer Successfully", OK);
    }

}
