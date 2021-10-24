package com.scms.supplychainmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponse {

    private Long orderDetailId;

    private Long productId;

    private Double quantity;

    private Long orderId;

    private Long priceBookId;

    private Long orderDetailsStatusId;
}
