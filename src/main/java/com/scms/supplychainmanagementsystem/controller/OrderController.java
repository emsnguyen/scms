package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.OrderRequest;
import com.scms.supplychainmanagementsystem.dto.OrderResponse;
import com.scms.supplychainmanagementsystem.dto.OrderStatusDto;
import com.scms.supplychainmanagementsystem.entity.Order;
import com.scms.supplychainmanagementsystem.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final IOrderService iOrderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders(@RequestParam(required = false) String orderCode,
                                                            @RequestParam(required = false) String customerName,
                                                            @RequestParam(required = false) Long orderStatusId,
                                                            @RequestParam(required = false) Long warehouseId,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        log.info("[Start OrderController - getAllOrders]");
        List<Order> orderList;
        Page<Order> orderPage;
        Pageable pageable = PageRequest.of(page, size);
        orderPage = iOrderService.getAllOrders(orderCode, customerName, orderStatusId, warehouseId, pageable);

        orderList = orderPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", orderList);
        response.put("currentPage", orderPage.getNumber());
        response.put("totalItems", orderPage.getTotalElements());
        response.put("totalPages", orderPage.getTotalPages());
        if (!orderPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End OrderController - getAllOrders]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Not required [orderId]")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("[Start OrderController - createOrder]");
        Map<String, Object> result = new HashMap<>();
        iOrderService.createOrder(orderRequest);
        result.put("message", "Order Created Successfully");
        log.info("[End OrderController - createOrder]");
        return status(CREATED).body(result);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long orderId) {
        log.info("[Start OrderController - getOrderById = " + orderId + "]");
        Map<String, Object> result = new HashMap<>();
        OrderResponse orderResponse = iOrderService.getOrderById(orderId);
        result.put("data", orderResponse);
        result.put("message", OK);
        log.info("[End OrderController - getOrderById = " + orderId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAllOrderStatus() {
        log.info("[Start OrderController - getAllOrderStatus]");
        Map<String, Object> result = new HashMap<>();
        List<OrderStatusDto> orderStatusDtoList = iOrderService.getAllOrderStatus();
        result.put("data", orderStatusDtoList);
        result.put("message", OK);
        log.info("[End OrderController - getAllOrderStatus]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access.")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderRequest orderRequest) {
        log.info("[Start OrderController - updateOrder ID = " + orderId + "]");
        Map<String, Object> result = new HashMap<>();
        orderRequest.setOrderId(orderId);
        iOrderService.updateOrder(orderRequest);
        result.put("message", "Order Updated Successfully");
        log.info("[End OrderController - updateOrder ID = " + orderId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<Map<String, Object>> deleteOrderByOrderId(@PathVariable Long orderId) {
        log.info("[Start OrderController - Delete deleteOrderByOrderId = " + orderId + "]");
        Map<String, Object> result = new HashMap<>();
        iOrderService.deleteOrderByOrderId(orderId);
        result.put("message", "Order Deleted Successfully");
        log.info("[End OrderController - Delete deleteOrderByOrderId = " + orderId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{orderId}/{orderStatusId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable Long orderId, @PathVariable Long orderStatusId) {
        log.info("[Start OrderController - updateOrderStatus - Order ID = " + orderId + "]");
        Map<String, Object> result = new HashMap<>();
        iOrderService.updateOrderStatus(orderId, orderStatusId);
        result.put("message", "Update Order Status Successfully");
        log.info("[End OrderController - updateOrderStatus - Order ID = " + orderId + "]");
        return status(HttpStatus.OK).body(result);
    }

}
