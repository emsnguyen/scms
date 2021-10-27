package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Site;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SiteDto {
    private Long siteId;

    private String siteName;

    private String address ;

    private Long warehouseId;

    public SiteDto(Site site) {
        this.siteId = site.getSiteId();
        this.siteName = site.getSiteName();
        this.address = site.getAddress();
        this.warehouseId = site.getWarehouse().getWarehouseID();
    }
}
