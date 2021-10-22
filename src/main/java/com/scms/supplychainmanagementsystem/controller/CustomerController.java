package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.CustomerRepository;
import com.scms.supplychainmanagementsystem.service.ICustomerService;
import com.scms.supplychainmanagementsystem.validation.MyValidation;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/manage/customer")
public class CustomerController {

    private ICustomerService iCustomerService;
    private CustomerRepository customerRepository;
    private final UserCommon userCommon;
    private MyValidation validation;


    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllCustomerInWarehouse(@RequestParam(required = false) String customername,
                                                                         @RequestParam(required = false) Long warehouseId,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        log.info("[Start CustomerController - Get All Customer In Warehouse]");
        List<Customer> customerList;
        Page<Customer> customerPage;
        Pageable pageable = PageRequest.of(page, size);

        customerPage = iCustomerService.getAllCustomer(customername, warehouseId, pageable);

        customerList = customerPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", customerList);
        response.put("currentPage", customerPage.getNumber());
        response.put("totalItems", customerPage.getTotalElements());
        response.put("totalPages", customerPage.getTotalPages());
        if (!customerPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End CustomerController - Get All Customer In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access . Disable [createdBy,createdDate,customerId,lastModifiedBy] ,Disable warehouseId for MANAGER ,warehouseId NOT NULL for ADMIN")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        log.info("[Start CustomerController -  createCustomer " + customerDto.getEmail() + "]");
        iCustomerService.saveCustomer(customerDto);
        log.info("[End CustomerController -  createCustomer " + customerDto.getEmail() + "]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerByIdInWareHouse(@PathVariable Long customerId) {
        log.info("[Start CustomerController - Get Customer By ID]");
        Customer customer = iCustomerService.getCustomerByIdInWarehouse(customerId);
        if (customer != null) {
            CustomerDto customerDto = new CustomerDto(customer);
            log.info("[End CustomerController - Get Customer By ID]");
            return status(HttpStatus.OK).body(customerDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Disable [createdBy,createdDate,customerId,lastModifiedBy,warehouseId]")
    public ResponseEntity<String> updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerDto customerDto) {
        log.info("[Start CustomerController - Update Customer with email " + customerDto.getEmail() + "]");
        iCustomerService.updateCustomer(customerId, customerDto);
        log.info("[End CustomerController - Update Customer with email " + customerDto.getEmail() + "]");
        return new ResponseEntity<>("Update Customer Successfully", OK);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteCustomer(@PathVariable Long customerId) {
        log.info("[Start CustomerController - Get Customer By ID]");
        iCustomerService.deleteCustomer(customerId);
        log.info("[End CustomerController - Get Customer By ID]");
        return new ResponseEntity<>("Delete Customer Successfully", OK);
    }

    @GetMapping("/list-warehouse")
    public ResponseEntity<Map<String, Object>> getAllWarehouse() {
        log.info("[Start CustomerController - Get All Warehouse]");
        Map<String, Object> result = new HashMap<>();
        List<WarehouseDto> warehouseDtos = iCustomerService.getAllWarehouse();
        result.put("data", warehouseDtos);
        result.put("message", OK);
        log.info("[End CustomerController - Get All Warehouse]");
        return status(HttpStatus.OK).body(result);
    }

    @PostMapping("/import")
    public ResponseEntity<String> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        log.info("[Start CustomerController -  AddCustomer from Excel  ");
        iCustomerService.mapReapExcelDatatoDB(reapExcelDataFile);
        log.info("[End CustomerController - AddCustomer from Excel  ");
        return new ResponseEntity<>("Add Successfully", CREATED);

        }



    }

