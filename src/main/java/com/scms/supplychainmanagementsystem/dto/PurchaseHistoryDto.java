package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.PurchaseHistory;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseHistoryDto {

    private Long purchaseID;


    private Date PurchaseDate;

    private String UnitPrice;

    private Double Quantity;

    private Long supplierId;

    private Long materialId;

    private String createdBy;

    private String lastModifiedBy;

    private Long warehouseId;


    public PurchaseHistoryDto(PurchaseHistory purchaseHistory) {
        this.purchaseID = purchaseHistory.getPurchaseID();
        PurchaseDate = purchaseHistory.getPurchaseDate();
        UnitPrice = purchaseHistory.getUnitPrice();
        Quantity = purchaseHistory.getQuantity();
        this.warehouseId = purchaseHistory.getWarehouse().getWarehouseID();
        this.supplierId = purchaseHistory.getSupplier().getSupplierId();
        this.materialId = purchaseHistory.getMaterial().getMaterialID();
        this.createdBy = purchaseHistory.getCreatedBy().getUsername();
        this.lastModifiedBy = purchaseHistory.getLastModifiedBy().getLastName();
    }
}
