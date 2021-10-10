package com.scms.supplychainmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private Long warehouseId;
    private String productName;
    private Long categoryId;
    private String quantityUnitOfMeasure;
    private boolean isActive;
    private String createdBy;
    private String lastModifiedBy;
}
