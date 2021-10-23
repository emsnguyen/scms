package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.OrderRequest;
import com.scms.supplychainmanagementsystem.dto.OrderResponse;
import com.scms.supplychainmanagementsystem.dto.OrderStatusDto;
import com.scms.supplychainmanagementsystem.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    void updateOrder(OrderRequest orderRequest);

    void createOrder(OrderRequest orderRequest);

    void deleteOrderByOrderId(Long orderId);

    OrderResponse getOrderById(Long orderId);

    Page<Order> getAllOrders(String orderCode, String customerName, Long orderStatusId, Long warehouseId, Pageable pageable);

    List<OrderStatusDto> getAllOrderStatus();

    void updateOrderStatus(Long orderId, Long orderStatusId);
}
