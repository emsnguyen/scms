package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.service.IMaterialService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/manage/material")
public class MaterialController {
    private IMaterialService iMaterialService;


//    @GetMapping
//    public ResponseEntity<List<MaterialDto>> getAllMaterial(Pageable page) {
//        log.info("[Start Material - list]");
//        Page<Customer> customerslist = iCustomerService.getAllCustomerInWarehouse(page);
//        log.info("[End Material - list]");
//        List<Customer> listcus = customerslist.getContent();
//        List<CustomerDto> listDto = new ArrayList<>();
//        for (Customer customer : listcus) {
//            CustomerDto cuss = new CustomerDto(customer);
//            listDto.add(cuss);
//        }
//        return status(HttpStatus.OK).body(listDto);
//    }

//    @GetMapping("/list1")
//    public ResponseEntity<Map<String, Object>> getAllCustomerInWarehouse(@RequestParam(required = false) String username,
//                                                                      @RequestParam(required = false) Long roleId,
//                                                                      @RequestParam(required = false) Long warehouseId,
//                                                                      @RequestParam(defaultValue = "1") int page,
//                                                                      @RequestParam(defaultValue = "10") int size) {
//        log.info("[Start UserController - Get All Users In Warehouse]");
//        List<User> userList;
//        Page<User> userPage;
//        Pageable pageable = PageRequest.of(page, size);
//
//        userPage = iUserService.getAllUsers(username, roleId, warehouseId, pageable);
//
//        userList = userPage.getContent();
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("data", userList);
//        response.put("currentPage", userPage.getNumber());
//        response.put("totalItems", userPage.getTotalElements());
//        response.put("totalPages", userPage.getTotalPages());
//        if (!userPage.isEmpty()) {
//            response.put("message", HttpStatus.OK);
//        } else {
//            response.put("message", "EMPTY_RESULT");
//        }
//        log.info("[End UserController - Get All Users In Warehouse]");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createMaterial(@Valid @RequestBody MaterialDto materialDto) {
        log.info("[Start MaterialController -  createMaterial " + materialDto.getMaterialName() + "]");
        iMaterialService.saveMaterial(materialDto);
        log.info("[End MaterialController -  createMaterial " + materialDto.getMaterialName() + "]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<MaterialDto> getMaterialByIdInWareHouse(@PathVariable Long materialId) {
        log.info("[Start MaterialController - Get Material By ID]");
        Material material = iMaterialService.getMaterialByIdInWarehouse(materialId);
        if (material != null) {
            MaterialDto materialDto = new MaterialDto(material);
            log.info("[End MaterialController - Get Material By ID]");
            return status(HttpStatus.OK).body(materialDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateMaterial(@PathVariable Long materialId, @Valid @RequestBody MaterialDto materialDto) {
        log.info("[Start MaterialController - Update Material with name " + materialDto.getMaterialName() + "]");
        iMaterialService.updateMaterial(materialId, materialDto);
        log.info("[End MaterialController - Update material with name " + materialDto.getMaterialName() + "]");
        return new ResponseEntity<>("Update Material Successfully", OK);
    }

    @DeleteMapping("/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteMaterial(@PathVariable Long materialId) {
        log.info("[Start MaterialController - Get Material By ID]");
        iMaterialService.deleteMaterial(materialId);
        log.info("[End MaterialController - Get Material By ID]");
        return new ResponseEntity<>("Delete Material Successfully", OK);
    }
}
