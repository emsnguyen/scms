package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.service.IMaterialService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/manage/material")
public class MaterialController {
    private IMaterialService iMaterialService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMaterialInWarehouse(@RequestParam(required = false) String materialName,
                                                                         @RequestParam(required = false) Long warehouseId,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        log.info("[Start MaterialController - Get All Material In Warehouse]");
        List<Material> MaterialList;
        Page<Material> MaterialPage;
        Pageable pageable = PageRequest.of(page, size);

        MaterialPage = iMaterialService.getAllMaterial(materialName,warehouseId, pageable);

        MaterialList = MaterialPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", MaterialList);
        response.put("currentPage", MaterialPage.getNumber());
        response.put("totalItems", MaterialPage.getTotalElements());
        response.put("totalPages", MaterialPage.getTotalPages());
        if (!MaterialPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End CustomerController - Get All Customer In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/warehouse/{warehouseid}")
    public ResponseEntity<List<Material>> getMaterialInWarehouse(@PathVariable Long warehouseid) {
        log.info("[Start PurchaseHistoryController - Get PurchaseHistory By ID]");

        List<Material> materialArrayList = new ArrayList<>();
        materialArrayList=iMaterialService.getMaterialInWareHouse(warehouseid);
        return new ResponseEntity<>(materialArrayList, HttpStatus.OK);
    }


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
