package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Inventory;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {
    private Long inventoryId;

    private Long personCheck;

    private Instant dateCheck;

    private String description;

    private double shortageQuantity;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Long warehouseId;

    private Long productId;

    private Long statusId;

    private String createdBy;

    private String lastModifiedBy;

    public InventoryDto(Inventory inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.personCheck = inventory.getPersonCheck();
        this.dateCheck = inventory.getDateCheck();
        this.description = inventory.getDescription();
        this.shortageQuantity = inventory.getShortageQuantity();
        this.createdDate = inventory.getCreatedDate();
        this.lastModifiedDate = inventory.getLastModifiedDate();
        this.warehouseId = inventory.getWarehouse().getWarehouseID();
        this.productId = inventory.getProduct().getProductId();
        this.statusId = inventory.getStatus().getStatusId();
        this.createdBy = inventory.getCreatedBy().getUsername();
        this.lastModifiedBy = inventory.getLastModifiedBy().getUsername();
    }
}
