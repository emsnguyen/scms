package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.CustomerRepository;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.ICustomerService;
import com.scms.supplychainmanagementsystem.validation.MyValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {


    @Autowired
    private CustomerRepository customerRepository;
    private final UserCommon userCommon;
    private final WarehouseRepository warehouseRepository;
    private MyValidation validation;


    @Override
    public Page<Customer> getAllCustomerInWarehouse(Pageable pageable) {
        User currentUser = userCommon.getCurrentUser();
        return customerRepository.findByWarehouse(userCommon.getCurrentUser().getWarehouse().getWarehouseID(), pageable);
    }


    @Override
    public Customer getCustomerByIdInWarehouse(Long customerId) {
        User currentUser = userCommon.getCurrentUser();
        Customer customer = new Customer();
        if (currentUser.getRole().getRoleID() != 1) {
            customer = customerRepository.findByCustomerIdAnhInWarehouse(customerId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
        } else {
            customer = customerRepository.findByCustomerId(customerId);
        }
        return customer;
    }


    @Override
    public void updateCustomer(Long customerid, CustomerDto customerDto) {
        log.info("[Start CustomerService - UpdateCustomer with email: " + customerDto.getEmail() + "]");

        log.info("[Start get current user]");

        User currentUser = userCommon.getCurrentUser();


        log.info("[End get current user : " + currentUser.getUsername() + "]");


        Warehouse warehouse = new Warehouse();
        if (currentUser.getRole().getRoleID() != 1) {
            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());
            if (currentUser.getWarehouse().getWarehouseID() != customerRepository.findByCustomerId(customerid).getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        } else {
            warehouse.setWarehouseID(customerDto.getWarehouseId());
        }

        Customer customer = Customer.builder()
                .customerId(customerid)
                .customerCode(customerDto.getCustomerCode())
                .CustomerType(customerDto.getCustomerType())
                .customerName(customerDto.getCustomerName())
                .email(customerDto.getEmail())
                .warehouse(warehouse)
                .phone(customerDto.getPhone())
                .DateOfBirth(customerDto.getDateOfBirth())
                .Gender(customerDto.getGender())
                .Facebook(customerDto.getFacebook())
                .CompanyName(customerDto.getCompanyName())
                .Note(customerDto.getNote())
                .TaxCode(customerDto.getTaxCode())
                .district(District.builder().districtID(customerDto.getDistrictId()).build())
                .streetAddress(customerDto.getStreetAddress())
                .createdDate(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start update customer " + customer.getEmail() + " to database]");
        customerRepository.save(customer);
        log.info("[End update customer " + customer.getEmail() + " to database]");
        log.info("[End CustomerService - updateCustomer with Email: " + customer.getEmail() + "]");
    }

    @Override
    public void saveCustomer(CustomerDto customerDto) {
        log.info("[Start CustomerService - saveCustomer with email: " + customerDto.getEmail() + "]");
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new AppException("Email exists");
        }
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();
        if (currentUser.getRole().getRoleID() != 1) {
            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());
        } else {
            warehouse.setWarehouseID(customerDto.getWarehouseId());
        }

        Customer customer = Customer.builder()
                .customerCode(customerDto.getCustomerCode())
                .CustomerType(customerDto.getCustomerType())
                .customerName(customerDto.getCustomerName())
                .email(customerDto.getEmail())
                .warehouse(warehouse)
                .phone(customerDto.getPhone())
                .DateOfBirth(customerDto.getDateOfBirth())
                .Gender(customerDto.getGender())
                .Facebook(customerDto.getFacebook())
                .CompanyName(customerDto.getCompanyName())
                .Note(customerDto.getNote())
                .TaxCode(customerDto.getTaxCode())
                .district(District.builder().districtID(customerDto.getDistrictId()).build())
                .streetAddress(customerDto.getStreetAddress())
                .createdDate(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start save customer " + customer.getEmail() + " to database]");
        customerRepository.saveAndFlush(customer);
        log.info("[End save customer " + customer.getEmail() + " to database]");
        log.info("[End CustomerService - saveCustomer with Email: " + customer.getEmail() + "]");
    }

    @Override
    public void deleteCustomer(Long customerid) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            customerRepository.deleteCustomer(customerid, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
        } else {
            customerRepository.deleteCustomerAdmin(customerid);
        }
    }

    @Override
    public Page<Customer> getAllCustomer(String customername, Long warehouseId, Pageable pageable) {
        log.info("[Start CustomerService - Get All Customer]");
        Page<Customer> customerPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() != 1) {
            customerPage = customerRepository.filterInOneWarehouse(customername, wh.getWarehouseID(), pageable);

        } else {
            customerPage = customerRepository.filterAllWarehouses(customername, warehouseId, pageable);
        }
        log.info("[End CustomerService - Get All Customer]");
        return customerPage;
    }

    @Override
    public List<WarehouseDto> getAllWarehouse() {
        log.info("[Start CustomerService - Get All Warehouse]");
        List<WarehouseDto> warehouseList = warehouseRepository.findAll()
                .stream().map(x -> new WarehouseDto(x.getWarehouseID(), x.getWarehouseName(), x.getAddress())).collect(Collectors.toList());
        log.info("[End UserService - Get All Roles]");
        return warehouseList;
    }

    @Override
    public void mapReapExcelDatatoDB(MultipartFile reapExcelDataFile) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        if (currentUser.getRole().getRoleID() != 1) {
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());

            System.out.println("so hang" + worksheet.getPhysicalNumberOfRows());
            List<Customer> customerList = new ArrayList<>();

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);
                Date date = null;
                if (row.getCell(5) != null) {
                    date = row.getCell(5).getDateCellValue();
                } else {
                    ex(" DateofBirth ", i + 1);
                }

                DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                String convertedDate = parser.format(date);

                String sex = null;
                if (row.getCell(6) != null) {
                    sex = row.getCell(6).getStringCellValue().toLowerCase(Locale.ROOT).trim();
                    if (sex.equals("male") || sex.equals("nam") || sex.equals("trai")) {
                        sex = "TRUE";
                    } else {
                        if (sex.equals("female") || sex.equals("nu") || sex.equals("nữ") || sex.equals("gái") || sex.equals("gai")) {
                            sex = "FALSE";
                        } else {
                            ex("Gender", i + 1);
                        }
                    }
                } else {
                    ex("Gender", i + 1);
                }

                Customer customer = Customer.builder()
                        .customerCode(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : ex(" customerCode ", i + 1))
                        .CustomerType(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : ex(" customerType ", i + 1))
                        .customerName(row.getCell(2).getStringCellValue())
                        .email(row.getCell(3) != null ? validation.checkInputEmail(row.getCell(3).getStringCellValue(), i + 1) : ex(" Email ", i + 1))
                        .warehouse(warehouse)
                        .phone(row.getCell(4) != null ? validation.checkInputPhone(row.getCell(4).getStringCellValue(), i + 1) : ex(" Phone ", i + 1))
                        .DateOfBirth(LocalDate.parse(convertedDate))
                        .Gender(Boolean.parseBoolean(sex))
                        .Facebook(row.getCell(7) != null ? row.getCell(7).getStringCellValue() : ex(" Facebook ", i + 1))
                        .CompanyName(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : ex(" CompanyName ", i + 1))
                        .Note(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : ex(" Note ", i + 1))
                        .TaxCode(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : ex(" TaxCode ", i + 1))
                        .district(District.builder()
                                .districtID(0L).build())
                        .streetAddress(row.getCell(11) != null ? row.getCell(11).getStringCellValue() : ex(" StreetAddress ", i + 1))
                        .createdDate(Instant.now())
                        .createdBy(currentUser)
                        .lastModifiedBy(currentUser)
                        .build();
                customerList.add(customer);

                System.out.println(customer.toString());
            }
            for (Customer customer : customerList) {
                customerRepository.saveAndFlush(customer);
            }
        } else {
            System.out.println("so hang" + worksheet.getPhysicalNumberOfRows());
            List<Customer> customerList = new ArrayList<>();

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);
                Warehouse warehouse = new Warehouse();
                Date date = null;
                if (row.getCell(5) != null) {
                    date = row.getCell(5).getDateCellValue();
                } else {
                    ex(" DateofBirth ", i + 1);
                }

                warehouse.setWarehouseID(Long.parseLong(row.getCell(12).getRawValue()));
                DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                String convertedDate = parser.format(date);

                String sex = null;
                if (row.getCell(6) != null) {
                    sex = row.getCell(6).getStringCellValue().toLowerCase(Locale.ROOT).trim();
                    if (sex.equals("male") || sex.equals("nam") || sex.equals("trai")) {
                        sex = "TRUE";
                    } else {
                        if (sex.equals("female") || sex.equals("nu") || sex.equals("nữ") || sex.equals("gái") || sex.equals("gai")) {
                            sex = "FALSE";
                        } else {
                            ex("Gender", i + 1);
                        }
                    }
                } else {
                    ex("Gender", i + 1);
                }

                Customer customer = Customer.builder()
                        .customerCode(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : ex(" customerCode ", i + 1))
                        .CustomerType(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : ex(" customerType ", i + 1))
                        .customerName(row.getCell(2).getStringCellValue())
                        .email(row.getCell(3) != null ? validation.checkInputEmail(row.getCell(3).getStringCellValue(), i + 1) : ex(" Email ", i + 1))
                        .warehouse(warehouse)
                        .phone(row.getCell(4) != null ? validation.checkInputPhone(row.getCell(4).getStringCellValue(), i + 1) : ex(" Phone ", i + 1))
                        .DateOfBirth(LocalDate.parse(convertedDate))
                        .Gender(Boolean.parseBoolean(sex))
                        .Facebook(row.getCell(7) != null ? row.getCell(7).getStringCellValue() : ex(" Facebook ", i + 1))
                        .CompanyName(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : ex(" CompanyName ", i + 1))
                        .Note(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : ex(" Note ", i + 1))
                        .TaxCode(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : ex(" TaxCode ", i + 1))
                        .district(District.builder()
                                .districtID(0L).build())
                        .streetAddress(row.getCell(11) != null ? row.getCell(11).getStringCellValue() : ex(" StreetAddress ", i + 1))
                        .createdDate(Instant.now())
                        .createdBy(currentUser)
                        .lastModifiedBy(currentUser)
                        .build();
                customerList.add(customer);

                System.out.println(customer.toString());
            }
            for (Customer customer : customerList) {
                customerRepository.saveAndFlush(customer);
            }
        }
    }

    public String ex(String loi, int index) throws IOException {
        throw new AppException("Lỗi nhận dữ liệu " + loi + " tai hàng " + index);
    }
}


