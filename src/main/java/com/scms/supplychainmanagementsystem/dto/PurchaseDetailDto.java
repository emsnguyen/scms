package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDetailDto {

    private Long purchaseDetailID;

    private String unitPrice;

    private Double quantity;

    private Long purchaseId;

    private Long materialId;

    public PurchaseDetailDto(PurchaseDetails purchaseDetails) {
        this.purchaseDetailID = purchaseDetails.getPurchaseDetailID();
        this.unitPrice = purchaseDetails.getUnitPrice();
        this.quantity = purchaseDetails.getQuantity();
        this.purchaseId = purchaseDetails.getPurchase().getPurchaseID();
        this.materialId = purchaseDetails.getMaterial().getMaterialID();
    }
}
