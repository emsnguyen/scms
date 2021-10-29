package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.OrderDetailsRequest;

public interface IOrderDetailStatus {
    void updateOrderDetailsStatus(Long orderDetailId ,String status);
}
