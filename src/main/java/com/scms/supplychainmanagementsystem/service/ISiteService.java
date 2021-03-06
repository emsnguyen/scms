package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.SiteDto;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import com.scms.supplychainmanagementsystem.entity.Site;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISiteService {
    Page<Site> getAllSite(Long warehouseId, Pageable pageble);

    void updateSite(Long siteId, SiteDto siteDto);

    void saveSite(SiteDto siteDto);

    void deleteSite(Long siteId);

     Site getSiteByIdInWarehouse(Long siteId);

}
