package com.scms.supplychainmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceBookEntryDto {

    private Long priceBookEntryId;

    private Double price;

    private Long productId;

    private Long priceBookId;

}
