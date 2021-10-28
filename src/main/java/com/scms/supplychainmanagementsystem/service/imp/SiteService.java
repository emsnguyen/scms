package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.SiteDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.repository.*;
import com.scms.supplychainmanagementsystem.service.ISiteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class SiteService implements ISiteService {

    private final UserCommon userCommon;
    private SiteRepository siteRepository;
    private WarehouseRepository warehouseRepository;


    @Override
    public Page<Site> getAllSite(Long warehouseId, Pageable pageable) {
        log.info("[Start SiteService - Get All Site]");
        Page<Site> sitePage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            sitePage = siteRepository.filterAllWarehouses(warehouseId, pageable);
        } else {
            sitePage = siteRepository.filterInOneWarehouse( wh.getWarehouseID(), pageable);
        }
        log.info("[End SiteService - Get All Site]");
        return sitePage;
    }

    @Override
    public void updateSite(Long siteId, SiteDto siteDto) {
        log.info("[Start SiteService - UpdateSite  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = warehouseRepository.getById(siteDto.getWarehouseId());

        Site site = Site.builder()
                .siteId(siteId)
                .siteName(siteDto.getSiteName())
                .address(siteDto.getAddress())
                .warehouse(warehouse)
                .build();
        log.info("[Start SiteService - UpdateSite  to database]");
        siteRepository.save(site);
        log.info("[Start SiteService - UpdateSite  to database ]");
        log.info("[End SiteService - updateSite  to database ]");
    }

    @Override
    public void saveSite(SiteDto siteDto) {
        log.info("[Start SiteService - saveSite  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

       Warehouse warehouse = warehouseRepository.getById(siteDto.getWarehouseId());

        Site site = Site.builder()
               .siteName(siteDto.getSiteName())
                .address(siteDto.getAddress())
                .warehouse(warehouse)
                .build();
        log.info("[Start SiteService - saveSite  to database]");
        siteRepository.saveAndFlush(site);
        log.info("[Start SiteService - saveSite  to database ]");
        log.info("[End SiteService - saveSite  to database ]");
    }


    @Override
    public Site getSiteByIdInWarehouse(Long siteId) {
        User currentUser = userCommon.getCurrentUser();
        Site site = new Site();
        if (currentUser.getRole().getRoleID() != 1) {
            site = siteRepository.findBySiteId(siteId, currentUser.getWarehouse().getWarehouseID());
        } else {
            site = siteRepository.getById(siteId);
    }
        return site;
    }

    @Override
    public void deleteSite(Long siteId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            siteRepository.deleteSite(siteId, currentUser.getWarehouse().getWarehouseID());
        } else {
            siteRepository.deleteSiteAdmin(siteId);
        }
    }}


