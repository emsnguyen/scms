package com.scms.supplychainmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceBookDto {
    private Long priceBookId;

    private String priceBookName;

    private Boolean isStandardPriceBook;

    private Boolean isActive;

    private Long warehouseId;

}
