package com.scms.supplychainmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Long orderId;

    private Long warehouseId;

    private Long contactId;

    private String note;
}
