package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.OrderDetailsRequest;
import com.scms.supplychainmanagementsystem.dto.OrderDetailsResponse;
import com.scms.supplychainmanagementsystem.entity.OrderDetails;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.service.IOrderDetailsService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
public class OrderDetailsController {
    private final IOrderDetailsService iOrderDetailsService;

    @GetMapping("/{orderId}/order-details")
    public ResponseEntity<Map<String, Object>> getAllOrderDetailsByOrderId(@PathVariable Long orderId,
                                                                           @RequestParam(required = false) String productName,
                                                                           @RequestParam(required = false) Long orderDetailsStatusId,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        log.info("[Start OrderDetailsController - getAllOrderDetails]");
        List<OrderDetails> orderDetailsList;
        Page<OrderDetails> orderDetailsPage;
        Pageable pageable = PageRequest.of(page, size);
        orderDetailsPage = iOrderDetailsService.getAllOrderDetailsByOrderId(orderId, productName, orderDetailsStatusId, pageable);

        orderDetailsList = orderDetailsPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDetailsList);
        response.put("currentPage", orderDetailsPage.getNumber());
        response.put("totalItems", orderDetailsPage.getTotalElements());
        response.put("totalPages", orderDetailsPage.getTotalPages());
        if (!orderDetailsPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End OrderDetailsController - getAllOrderDetails]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/order-details")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Not required [OrderDetailsId]")
    public ResponseEntity<Map<String, Object>> createOrderDetailsByOrderId(@PathVariable Long orderId, @Valid @RequestBody OrderDetailsRequest orderDetailsRequest) {
        log.info("[Start OrderDetailsController - createOrderDetails]");
        Map<String, Object> result = new HashMap<>();
        orderDetailsRequest.setOrderId(orderId);
        iOrderDetailsService.createOrderDetailsByOrderId(orderDetailsRequest);
        result.put("message", "OrderDetails Created Successfully");
        log.info("[End OrderDetailsController - createOrderDetails]");
        return status(CREATED).body(result);
    }

    @GetMapping("/order-details/{orderDetailId}")
    public ResponseEntity<Map<String, Object>> getOrderDetailsById(@PathVariable Long orderDetailId) {
        log.info("[Start OrderDetailsController - getOrderDetailsById = " + orderDetailId + "]");
        Map<String, Object> result = new HashMap<>();
        OrderDetailsResponse orderDetailsResponse = iOrderDetailsService.getOrderDetailsById(orderDetailId);
        result.put("data", orderDetailsResponse);
        result.put("message", OK);
        log.info("[End OrderDetailsController - getOrderDetailsById = " + orderDetailId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/order-details/{orderDetailId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access.")
    public ResponseEntity<Map<String, Object>> updateOrderDetails(@PathVariable Long orderDetailId, @RequestBody OrderDetailsRequest orderDetailsRequest) {
        log.info("[Start OrderDetailsController - updateOrderDetails ID = " + orderDetailId + "]");
        Map<String, Object> result = new HashMap<>();
        iOrderDetailsService.updateOrderDetails(orderDetailsRequest, orderDetailId);
        result.put("message", "OrderDetails Updated Successfully");
        log.info("[End OrderDetailsController - updateOrderDetails ID = " + orderDetailId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/order-details/{orderDetailId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<Map<String, Object>> deleteOrderDetailsById(@PathVariable Long orderDetailId) {
        log.info("[Start OrderDetailsController - Delete deleteOrderDetailsById = " + orderDetailId + "]");
        Map<String, Object> result = new HashMap<>();
        try {
            iOrderDetailsService.deleteOrderDetailsById(orderDetailId);
        } catch (DataIntegrityViolationException e) {
            throw new AppException("Some resource using this data");
        }
        result.put("message", "OrderDetails Deleted Successfully");
        log.info("[End OrderDetailsController - Delete deleteOrderDetailsById = " + orderDetailId + "]");
        return status(HttpStatus.OK).body(result);
    }
}
