package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.DistrictResponse;
import com.scms.supplychainmanagementsystem.dto.ProvinceResponse;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.service.ILocationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
@Slf4j
public class LocationController {
    private final ILocationService iLocationService;

    @GetMapping("/provinces")
    @ApiOperation(value = "Display all provinces")
    public ResponseEntity<Map<String, Object>> listProvinces() {
        log.info("[Start LocationController - listProvinces]");
        List<ProvinceResponse> provinceResponseList = iLocationService.getAllProvinces();
        Map<String, Object> result = new HashMap<>();
        result.put("data", provinceResponseList);
        result.put("message", OK);
        log.info("[End LocationController - listProvinces]");
        return status(HttpStatus.OK).body(result);
    }

    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<Map<String, Object>> getDistrictsByProvinceId(@PathVariable Long provinceId) {
        log.info("[Start LocationController - getDistrictsByProvinceId]");
        Map<String, Object> result = new HashMap<>();
        List<DistrictResponse> districtResponseList = iLocationService.getListDistrictsByProvinceID(provinceId);
        result.put("data", districtResponseList);
        result.put("message", OK);
        log.info("[End LocationController - getDistrictsByProvinceId]");
        return status(OK).body(result);
    }

    @GetMapping("/districts")
    public ResponseEntity<Map<String, Object>> getDistrictByDistrictId(@RequestParam Long districtId) {
        log.info("[Start LocationController - getDistrictsByProvinceId]");
        Map<String, Object> result = new HashMap<>();
        DistrictResponse districtResponses = iLocationService.getDistrictsByProvinceId(districtId);
        result.put("data", districtResponses);
        result.put("message", OK);
        log.info("[End LocationController - getDistrictsByProvinceId]");
        return status(OK).body(result);
    }

    @GetMapping("/warehouse")
    public ResponseEntity<Map<String, Object>> getAllWarehouses() {
        log.info("[Start LocationController - getAllWarehouse]");
        Map<String, Object> result = new HashMap<>();
        List<WarehouseDto> warehouseDtoList = iLocationService.getAllWarehouses();
        result.put("data", warehouseDtoList);
        result.put("message", OK);
        log.info("[End LocationController - getAllWarehouse]");
        return status(OK).body(result);
    }

}
