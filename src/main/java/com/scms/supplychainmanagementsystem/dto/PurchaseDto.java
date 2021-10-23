package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Purchase;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDto {

    private Long purchaseID;

    private LocalDate purchaseDate;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Long supplierId;

    private Long warehouseId;

    private String createdBy;

    private String lastModifiedBy;

    public PurchaseDto(Purchase purchase) {
        this.purchaseID = purchase.getPurchaseID();
        this.purchaseDate = purchase.getPurchaseDate();
        this.createdDate = purchase.getCreatedDate();
        this.lastModifiedDate = purchase.getLastModifiedDate();
        this.supplierId = purchase.getSupplier().getSupplierId();
        this.warehouseId = purchase.getWarehouse().getWarehouseID();
        this.createdBy = purchase.getCreatedBy().getUsername();
        this.lastModifiedBy = purchase.getLastModifiedBy().getUsername();
    }
}
