package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.GenerateCode;
import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.OrderRequest;
import com.scms.supplychainmanagementsystem.dto.OrderResponse;
import com.scms.supplychainmanagementsystem.dto.OrderStatusDto;
import com.scms.supplychainmanagementsystem.entity.Order;
import com.scms.supplychainmanagementsystem.entity.OrderStatus;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.ContactDeliveryRepository;
import com.scms.supplychainmanagementsystem.repository.OrderRepository;
import com.scms.supplychainmanagementsystem.repository.OrderStatusRepository;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.IOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserCommon userCommon;
    private final WarehouseRepository warehouseRepository;
    private final ContactDeliveryRepository contactDeliveryRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final GenerateCode generateCode;

    @Override
    public void updateOrder(OrderRequest orderRequest) {
        log.info("[Start OrderService - updateOrder ID = " + orderRequest.getOrderId() + "]");
        if (checkAccessOrder(orderRequest.getOrderId())) {
            Order order = orderRepository.findById(orderRequest.getOrderId())
                    .orElseThrow(() -> new AppException("Order not found"));
            User current = userCommon.getCurrentUser();
            if (current.getRole().getRoleID() == 1) {
                order.setWarehouse(warehouseRepository.findById(orderRequest.getWarehouseId())
                        .orElseThrow(() -> new AppException("Warehouse not found")));
            }
            order.setContactDelivery(contactDeliveryRepository.findById(orderRequest.getContactId())
                    .orElseThrow(() -> new AppException("Contact Delivery not found")));
            order.setNote(orderRequest.getNote());
            order.setLastModifiedBy(current);
            log.info("[Start Save Order " + orderRequest.getOrderId() + " to database]");
            orderRepository.saveAndFlush(order);
            log.info("[End Save Order " + orderRequest.getOrderId() + " to database]");
            log.info("[End OrderService - updateOrder ID = " + orderRequest.getOrderId() + "]");
        } else {
            throw new AppException("Not allow to access this resource");
        }

    }

    @Override
    public void createOrder(OrderRequest orderRequest) {
        log.info("[Start OrderService - createOrder]");
        User current = userCommon.getCurrentUser();
        Order order = new Order();
        if (orderRequest.getContactId() == null) {
            throw new AppException("Not fill in all required fields");
        }
        order.setContactDelivery(contactDeliveryRepository.getById(orderRequest.getContactId()));

        if (current.getRole().getRoleID() == 1) {
            order.setWarehouse(warehouseRepository.findById(orderRequest.getWarehouseId())
                    .orElseThrow(() -> new AppException("Warehouse not found")));
        } else {
            order.setWarehouse(current.getWarehouse());
        }
        order.setNote(orderRequest.getNote());
        order.setCreatedDate(Instant.now());

        order.setOrderStatus(orderStatusRepository.getById(1L));
        order.setCreatedBy(current);
        log.info("[Start Save Order to database]");
        orderRepository.saveAndFlush(order);
        //log.info("Generate Order Code");
        //order.setOrderCode(generateCode.genCodeByDate("DH") + order.getOrderId());
        //orderRepository.saveAndFlush(order);
        log.info("[End Save Order ID to database]");
        log.info("[End OrderService - createOrder]");

    }

    @Override
    public void deleteOrderByOrderId(Long orderId) {
        log.info("[Start OrderService - deleteOrderByOrderId = " + orderId + "]");
        if (checkAccessOrder(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new AppException("Not allow to delete this resource");
        }
        log.info("[End OrderService - deleteOrderByOrderId = " + orderId + "]");

    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        log.info("[Start OrderService - getOrderById = " + orderId + "]");
        OrderResponse orderResponse = new OrderResponse();
        if (checkAccessOrder(orderId)) {
            Order order = orderRepository.getById(orderId);
            orderResponse.setOrderId(orderId);
            orderResponse.setOrderStatusId(order.getOrderStatus().getOrderStatusID());
            orderResponse.setOrderCode(order.getOrderCode());
            orderResponse.setWarehouseId(order.getWarehouse().getWarehouseID());
            orderResponse.setContactDeliveryId(order.getContactDelivery().getContactID());
            orderResponse.setCreatedDate(order.getCreatedDate());
            orderResponse.setLastModifiedDate(order.getLastModifiedDate());
            orderResponse.setNote(order.getNote());
            orderResponse.setCreatedBy(order.getCreatedBy().getUsername());
            orderResponse.setLastModifiedBy(order.getLastModifiedBy() != null ? order.getLastModifiedBy().getUsername() : null);
        } else {
            throw new AppException("Not allow to access this resource");
        }
        log.info("[End OrderService - getOrderById = " + orderId + "]");
        return orderResponse;
    }

    @Override
    public Page<Order> getAllOrders(String orderCode, Long customerId, Long orderStatusId, Long warehouseId, Pageable pageable) {
        log.info("[Start OrderService - Get All Orders]");
        Page<Order> orderPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        if (current.getRole().getRoleID() == 1) {
            orderPage = orderRepository.filterAllWarehouses(orderCode, customerId, orderStatusId, warehouseId, pageable);
        } else {
            orderPage = orderRepository.filterInOneWarehouse(orderCode, customerId, orderStatusId, wh.getWarehouseID(), pageable);
        }
        log.info("[End OrderService - Get All Orders]");
        return orderPage;
    }

    @Override
    public List<OrderStatusDto> getAllOrderStatus() {
        log.info("[Start OrderService - getAllOrderStatus]");
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        List<OrderStatusDto> orderStatusDtoList = orderStatusList.stream()
                .map(o -> new OrderStatusDto(o.getOrderStatusID(), o.getStatus())).collect(Collectors.toList());
        log.info("[Start OrderService - getAllOrderStatus]");
        return orderStatusDtoList;
    }

    @Override
    public void updateOrderStatus(Long orderId, Long orderStatusId) {
        log.info("[Start OrderService - updateOrderStatus Order ID = " + orderId + "]");
        if (checkAccessOrder(orderId)) {
            Order order = orderRepository.getById(orderId);
            order.setOrderStatus(orderStatusRepository.getById(orderStatusId));
        } else {
            throw new AppException("Not allow to access this resource");
        }

        log.info("[Start OrderService - updateOrderStatus Order ID = " + orderId + "]");
    }

    public boolean checkAccessOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException("Order not found"));
        User current = userCommon.getCurrentUser();
        if (current.getRole().getRoleID() == 1) {
            return true;
        }
        if (order.getWarehouse() != null && current.getWarehouse() != null) {
            return order.getWarehouse().getWarehouseID().equals(current.getWarehouse().getWarehouseID());
        }
        return false;
    }
}
