package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.DistrictResponse;
import com.scms.supplychainmanagementsystem.dto.ProvinceResponse;
import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.Province;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.DistrictRepository;
import com.scms.supplychainmanagementsystem.repository.ProvinceRepository;
import com.scms.supplychainmanagementsystem.service.ILocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class LocationService implements ILocationService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    @Override
    public List<ProvinceResponse> getAllProvinces() {
        log.info("[Start LocationService - Get All Provinces]");
        log.info("[Start Get All Provinces from database]");
        List<Province> provinceRepositoryAll = provinceRepository.findAll();
        log.info("[End Get All Provinces from database]");
        List<ProvinceResponse> provinceResponseList =
                provinceRepositoryAll.stream()
                        .map(p -> new ProvinceResponse(p.getProvinceID(), p.getProvinceName()))
                        .collect(Collectors.toList());
        log.info("[End LocationService - Get All Provinces]");
        return provinceResponseList;
    }

    @Override
    public List<DistrictResponse> getListDistrictsByProvinceID(Long provinceId) {
        log.info("[Start LocationService - Get Districts By ProvinceID " + provinceId + "]");
        List<District> districtList = districtRepository.getDistrictByProvince_ProvinceID(provinceId);
        List<DistrictResponse> districtResponseList =
                districtList.stream()
                        .map(d -> new DistrictResponse(d.getDistrictID(), d.getProvince().getProvinceID(), d.getDistrictName()))
                        .collect(Collectors.toList());
        log.info("[End LocationService - Get Districts By ProvinceID " + provinceId + "]");
        return districtResponseList;
    }

    @Override
    public DistrictResponse getDistrictsByProvinceId(Long districtId) {
        log.info("[Start LocationService - Get District By DistrictId " + districtId + "]");
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new AppException("Not Found DistrictId"));
        DistrictResponse districtResponse = DistrictResponse.builder()
                .districtID(districtId)
                .districtName(district.getDistrictName())
                .provinceID(district.getProvince().getProvinceID()).build();
        log.info("[End LocationService - Get District By DistrictId " + districtId + "]");
        return districtResponse;
    }
}
