package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Product;
import com.scms.supplychainmanagementsystem.entity.Stock;
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
public class StockDto {

    private Long stockId;

    private Long productId;

    private Double availableQuantity;

    private Instant lastModifiedDate;

    public StockDto(Stock stock) {
        this.stockId = stock.getStockId();
        this.productId = stock.getProduct().getProductId();
        this.availableQuantity = stock.getAvailableQuantity();
        this.lastModifiedDate = stock.getLastModifiedDate();
    }
}
