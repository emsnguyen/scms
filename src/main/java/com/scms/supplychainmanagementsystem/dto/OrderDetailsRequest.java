package com.scms.supplychainmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsRequest {

    private Long orderId;

    private Long productId;

    private Double quantity;

    private Long priceBookId;

}
