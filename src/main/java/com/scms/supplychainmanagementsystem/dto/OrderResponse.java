package com.scms.supplychainmanagementsystem.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private String note;

    private String orderCode;

    private Long warehouseId;

    private Long contactDeliveryId;

    private Long orderStatusId;

    private String createdBy;

    private String lastModifiedBy;
}
