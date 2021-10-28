package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.OrderDetailsRequest;
import com.scms.supplychainmanagementsystem.dto.OrderDetailsResponse;
import com.scms.supplychainmanagementsystem.entity.OrderDetails;
import com.scms.supplychainmanagementsystem.entity.Stock;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.*;
import com.scms.supplychainmanagementsystem.service.IOrderDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderDetailsService implements IOrderDetailsService {
    private final OrderDetailsRepository orderDetailsRepository;
    private final UserCommon userCommon;
    private final ProductRepository productRepository;
    private final PriceBookRepository priceBookRepository;
    private final OrderDetailSttRepository orderDetailSttRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Override
    public void updateOrderDetails(OrderDetailsRequest orderDetailsRequest, Long orderDetailId) {
        log.info("[Start OrderDetailsService - updateOrderDetails ID = " + orderDetailId + "]");
        if (checkAccessOrderDetails(orderDetailId)) {
            OrderDetails orderDetails = orderDetailsRepository.findById(orderDetailId)
                    .orElseThrow(() -> new AppException("OrderDetails not found"));
            orderDetails.setProduct(productRepository.findById(orderDetailsRequest.getProductId())
                    .orElseThrow(() -> new AppException("Product not found")));
            orderDetails.setQuantity(orderDetailsRequest.getQuantity());
            orderDetails.setPriceBook(priceBookRepository.findById(orderDetailsRequest.getPriceBookId())
                    .orElseThrow(() -> new AppException("PriceBook not found")));
            if (checkOrderItemQtyAvailable(orderDetailsRequest.getProductId(), orderDetailsRequest.getQuantity())) {
                orderDetails.setOrderDetailsStatus(orderDetailSttRepository.getById(2L));
            } else {
                orderDetails.setOrderDetailsStatus(orderDetailSttRepository.getById(1L));
            }
            log.info("[Start Save OrderDetails " + orderDetailId + " to database]");
            orderDetailsRepository.saveAndFlush(orderDetails);
            log.info("[End Save OrderDetails " + orderDetailId + " to database]");
            log.info("[End OrderDetailsService - updateOrderDetails ID = " + orderDetailId + "]");
        } else {
            throw new AppException("Not allow to access this resource");
        }
    }

    @Override
    public void createOrderDetailsByOrderId(OrderDetailsRequest orderDetailsRequest) {
        log.info("[Start OrderDetailsService - createOrderDetailsByOrderId]");
        if (orderDetailsRequest.getProductId() == null || orderDetailsRequest.getPriceBookId() == null) {
            throw new AppException("Not fill in all required fields");
        }
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrder(orderRepository.findById(orderDetailsRequest.getOrderId())
                .orElseThrow(() -> new AppException("Order not found")));
        orderDetails.setPriceBook(priceBookRepository.findById(orderDetailsRequest.getPriceBookId())
                .orElseThrow(() -> new AppException("PriceBook not found")));
        orderDetails.setProduct(productRepository.findById(orderDetailsRequest.getProductId())
                .orElseThrow(() -> new AppException("Product not found")));
        if (checkOrderItemQtyAvailable(orderDetailsRequest.getProductId(), orderDetailsRequest.getQuantity())) {
            //Not enough stock
            orderDetails.setOrderDetailsStatus(orderDetailSttRepository.getById(2L));
        } else {
            //Enough stock
            orderDetails.setOrderDetailsStatus(orderDetailSttRepository.getById(1L));
        }
        log.info("[Start Save OrderDetails to database]");
        orderDetailsRepository.saveAndFlush(orderDetails);
        log.info("[End Save OrderDetails to database]");
        log.info("[End OrderDetailsService - createOrderDetailsByOrderId]");
    }

    @Override
    public void deleteOrderDetailsById(Long orderDetailId) {
        log.info("[Start OrderDetailsService - deleteOrderDetailsById = " + orderDetailId + "]");
        if (checkAccessOrderDetails(orderDetailId)) {
            OrderDetails orderDetails = orderDetailsRepository.getById(orderDetailId);
            Long orderStatusId = orderDetails.getOrder().getOrderStatus().getOrderStatusID();
            if (orderStatusId == 3) {
                Stock stock = stockRepository.findByProduct(orderDetails.getProduct());
                stock.setAvailableQuantity(stock.getAvailableQuantity() - orderDetails.getQuantity());
                log.info("Subtract quantity " + orderDetails.getQuantity() + " in Stock to " + stock.getAvailableQuantity());
                stockRepository.saveAndFlush(stock);
                log.info("Update Stock in database Successfully");
            } else if (orderStatusId > 3) {
                throw new AppException("Item delivered cannot be deleted");
            }
            orderDetailsRepository.deleteById(orderDetailId);
            log.info("Delete OrderDetails Successfully");
        } else {
            throw new AppException("Not allow to delete this resource");
        }
        log.info("[End OrderDetailsService - deleteOrderDetailsById = " + orderDetailId + "]");

    }

    @Override
    public OrderDetailsResponse getOrderDetailsById(Long orderDetailId) {
        log.info("[Start OrderDetailsService - getOrderDetailsById = " + orderDetailId + "]");
        OrderDetailsResponse orderDetailsResponse;
        if (checkAccessOrderDetails(orderDetailId)) {
            OrderDetails orderDetails = orderDetailsRepository.getById(orderDetailId);
            orderDetailsResponse = OrderDetailsResponse.builder()
                    .orderDetailId(orderDetailId)
                    .orderId(orderDetails.getOrder().getOrderId())
                    .priceBookId(orderDetails.getPriceBook().getPriceBookId())
                    .productId(orderDetails.getProduct().getProductId())
                    .quantity(orderDetails.getQuantity())
                    .orderDetailsStatusId(orderDetails.getOrderDetailsStatus().getOrderDetailStatusID())
                    .build();
        } else {
            throw new AppException("Not allow to access this resource");
        }
        log.info("[End OrderDetailsService - getOrderDetailsById = " + orderDetailId + "]");
        return orderDetailsResponse;
    }

    @Override
    public Page<OrderDetails> getAllOrderDetailsByOrderId(Long orderId, String productName, Long orderDetailsStatusId, Pageable pageable) {
        log.info("[Start OrderDetailsService - getAllOrderDetailsByOrderId = " + orderId + "]");
        Page<OrderDetails> orderDetailsPage;
        orderDetailsPage = orderDetailsRepository.getAllOrderDetailsByOrderId(orderId, productName, orderDetailsStatusId, pageable);
        log.info("[End OrderDetailsService - getAllOrderDetailsByOrderId = " + orderId + "]");
        return orderDetailsPage;
    }

    private boolean checkAccessOrderDetails(Long orderDetailId) {
        OrderDetails orderDetails = orderDetailsRepository.findById(orderDetailId)
                .orElseThrow(() -> new AppException("OrderDetails not found"));
        User current = userCommon.getCurrentUser();
        if (current.getRole().getRoleID() == 1) {
            return true;
        }
        if (orderDetails.getOrder().getWarehouse().getWarehouseID() != null && current.getWarehouse() != null) {
            return orderDetails.getOrder().getWarehouse().getWarehouseID().equals(current.getWarehouse().getWarehouseID());
        }
        return false;
    }

    private boolean checkOrderItemQtyAvailable(Long productId, Double quantity) {
        return stockRepository.checkQtyAvailable(productId, quantity);
    }
}
