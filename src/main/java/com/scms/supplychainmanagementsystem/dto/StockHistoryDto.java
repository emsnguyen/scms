package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Product;
import com.scms.supplychainmanagementsystem.entity.StockHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockHistoryDto {

    private Long stockHistoryID;

    private Double stockInQuantity;

    private Double unitCostPrice;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Long productId;

    private String createdBy;

    private String lastModifiedBy;

    public StockHistoryDto(StockHistory stockHistory) {
        this.stockHistoryID = stockHistory.getStockHistoryID();
        this.stockInQuantity = stockHistory.getStockInQuantity();
        this.unitCostPrice = stockHistory.getUnitCostPrice();
        this.createdDate = stockHistory.getCreatedDate();
        this.lastModifiedDate = stockHistory.getLastModifiedDate();
        this.productId = stockHistory.getProduct().getProductId();
        this.createdBy = stockHistory.getCreatedBy().getCreatedBy().getUsername();
        this.lastModifiedBy = stockHistory.getLastModifiedBy().getLastModifiedBy().getUsername();
    }
}
