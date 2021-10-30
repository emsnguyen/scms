package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PurchaseDetailDto;
import com.scms.supplychainmanagementsystem.dto.SiteDto;
import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import com.scms.supplychainmanagementsystem.entity.Site;
import com.scms.supplychainmanagementsystem.service.ISiteService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/manage/site")
public class SiteController {

   private ISiteService iSiteService ;

    @GetMapping("/siteid/{warehouseId}")
    public ResponseEntity<Map<String, Object>> getAllPurchaseDetail(@PathVariable Long warehouseId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        log.info("[Start SiteController - Get All Site In Warehouse]");
        List<Site> siteList;
        Page<Site> sitePage;
        Pageable pageable = PageRequest.of(page, size);

        sitePage = iSiteService.getAllSite(warehouseId, pageable);

        siteList = sitePage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", siteList);
        response.put("currentPage", sitePage.getNumber());
        response.put("totalItems", sitePage.getTotalElements());
        response.put("totalPages", sitePage.getTotalPages());
        if (!sitePage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End SiteController - Get All Site In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createSite(@Valid @RequestBody SiteDto siteDto) {
        log.info("[Start SiteController -  createSite  ]");
        iSiteService.saveSite(siteDto);
        log.info("[End SiteController -  createSite  ]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{siteid}")
    public ResponseEntity<SiteDto> getSiteById(@PathVariable Long siteid) {
        log.info("[Start SiteController - Get Site By ID]");
        Site site= iSiteService.getSiteByIdInWarehouse(siteid);
        if (site != null) {
            SiteDto siteDto = new SiteDto(site);
            log.info("[End SiteController - Get Site By ID]");
            return status(HttpStatus.OK).body(siteDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{siteid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateSite(@PathVariable Long siteid, @Valid @RequestBody SiteDto siteDto) {
        log.info("[Start SiteController - Update Site ]");
        iSiteService.updateSite(siteid, siteDto);
        log.info("[End SiteController - Update Site ]");
        return new ResponseEntity<>("Update Site Successfully", OK);
    }

    @DeleteMapping("/{siteid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteSite(@PathVariable Long siteid) {
        log.info("[Start SiteController - Delete Site By ID]");
        iSiteService.deleteSite(siteid);
        log.info("[End SiteController - Delete Site By ID]");
        return new ResponseEntity<>("Delete Site Successfully", OK);
    }
}
