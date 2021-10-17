package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMaterialService {

    Page<Material> getAllMaterial(String materialname, Long warehouseId, Pageable pageble);

    Material getMaterialByIdInWarehouse(Long MaterialId);

    void updateMaterial(Long materialId, MaterialDto materialDto);

    void saveMaterial(MaterialDto materialDto);

    void deleteMaterial(Long materialDto);

    List<Material> getMaterialInWareHouse(Long warehouseid);

}
