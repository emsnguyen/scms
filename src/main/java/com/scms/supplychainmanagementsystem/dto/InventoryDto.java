package com.scms.supplychainmanagementsystem.dto;

import java.time.Instant;

public class InventoryDto {
    private Long inventoryId;

    private Long personCheck;

    private Instant dateCheck;

    private Boolean isActive;

    private String description;

    private double currentQuantity;

    private double shortageQuantity;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Long warehouseId;

    private Long productId;

    private Long statusId;

    private String createdBy;

    private String lastModifiedBy;

//    public InventoryDto() {
//        this.inventoryId = inventoryId;
//        this.personCheck = personCheck;
//        this.dateCheck = dateCheck;
//        this.isActive = isActive;
//        this.description = description;
//        this.currentQuantity = currentQuantity;
//        this.shortageQuantity = shortageQuantity;
//        this.createdDate = createdDate;
//        this.lastModifiedDate = lastModifiedDate;
//        this.warehouseId = warehouseId;
//        this.productId = productId;
//        this.statusId = statusId;
//        this.createdBy = createdBy;
//        this.lastModifiedBy = lastModifiedBy;
//    }
}
