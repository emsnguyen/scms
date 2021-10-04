package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.DistrictResponse;
import com.scms.supplychainmanagementsystem.dto.ProvinceResponse;
import com.scms.supplychainmanagementsystem.service.ILocationService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
@Slf4j
public class LocationController {
    private final ILocationService iLocationService;

    @GetMapping("/provinces")
    @ApiOperation(value = "Display all provinces")
    public ResponseEntity<List<ProvinceResponse>> listProvince() {
        log.info("[Start LocationController - listProvince]");
        List<ProvinceResponse> provinceResponseList = iLocationService.getAllProvinces();
        log.info("[End LocationController - listProvince]");
        return status(HttpStatus.OK).body(provinceResponseList);
    }

    @GetMapping("/districts")
    public ResponseEntity<List<DistrictResponse>> getDistrictsByProvinceId(@RequestParam Long provinceId) {
        log.info("[Start LocationController - getDistrictsByProvinceId]");
        List<DistrictResponse> districtResponseList = iLocationService.getListDistrictsByProvinceID(provinceId);
        log.info("[End LocationController - getDistrictsByProvinceId]");
        return status(HttpStatus.OK).body(districtResponseList);
    }

}
