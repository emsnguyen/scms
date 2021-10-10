package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Material;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialDto {

    private Long materialID;


    private String MaterialName;

    private String quantityUnitOfMeasure ;

    private Instant createdDate ;

    private Instant lastModifiedDate;

    private Long warehouseId;

    private String createdBy;

    private String lastModifiedBy;

    public MaterialDto(Material material) {
        this.materialID = material.getMaterialID();
        MaterialName = material.getMaterialName();
        this.quantityUnitOfMeasure = material.getQuantityUnitOfMeasure();
        this.createdDate = material.getCreatedDate();
        this.lastModifiedDate = material.getLastModifiedDate();
        this.warehouseId = material.getWarehouse().getWarehouseID();
        this.createdBy = material.getCreatedBy().getUsername();
        this.lastModifiedBy = material.getLastModifiedBy().getUsername();
    }
}
