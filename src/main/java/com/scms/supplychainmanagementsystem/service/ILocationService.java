package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.DistrictResponse;
import com.scms.supplychainmanagementsystem.dto.ProvinceResponse;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;

import java.util.List;

public interface ILocationService {
    List<ProvinceResponse> getAllProvinces();

    List<DistrictResponse> getListDistrictsByProvinceID(Long provinceId);

    DistrictResponse getDistrictsByProvinceId(Long districtId);

    List<WarehouseDto> getAllWarehouses();
}
