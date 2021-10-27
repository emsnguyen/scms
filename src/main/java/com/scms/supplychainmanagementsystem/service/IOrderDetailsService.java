package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.OrderDetailsRequest;
import com.scms.supplychainmanagementsystem.dto.OrderDetailsResponse;
import com.scms.supplychainmanagementsystem.entity.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderDetailsService {
    void updateOrderDetails(OrderDetailsRequest orderDetailsRequest, Long orderDetailId);

    void createOrderDetailsByOrderId(OrderDetailsRequest orderDetailsRequest);

    void deleteOrderDetailsById(Long orderDetailId);

    OrderDetailsResponse getOrderDetailsById(Long orderDetailId);

    Page<OrderDetails> getAllOrderDetailsByOrderId(Long orderId, String productName, Long orderDetailsStatusId, Pageable pageable);

}
