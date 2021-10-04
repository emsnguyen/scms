package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.DistrictResponse;
import com.scms.supplychainmanagementsystem.dto.ProvinceResponse;

import java.util.List;

public interface ILocationService {
    List<ProvinceResponse> getAllProvinces();

    List<DistrictResponse> getListDistrictsByProvinceID(Long provinceId);
}
