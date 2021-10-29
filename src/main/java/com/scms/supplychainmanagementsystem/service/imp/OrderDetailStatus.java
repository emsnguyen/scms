package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.entity.OrderDetails;
import com.scms.supplychainmanagementsystem.entity.OrderDetailsStatus;
import com.scms.supplychainmanagementsystem.entity.Product;
import com.scms.supplychainmanagementsystem.entity.Stock;
import com.scms.supplychainmanagementsystem.repository.OrderDetailSttRepository;
import com.scms.supplychainmanagementsystem.repository.OrderDetailsRepository;
import com.scms.supplychainmanagementsystem.service.IOrderDetailStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class OrderDetailStatus implements IOrderDetailStatus {

    private OrderDetailSttRepository orderDetailSttRepository;
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public void updateOrderDetailsStatus(Long orderDetailId, String status) {

        OrderDetailsStatus orderDetailStatus = OrderDetailsStatus.builder()
               .orderDetailStatusID(orderDetailId)
                .status(status)
                .build();
        orderDetailSttRepository.save(orderDetailStatus);

        log.info("[Start OrderDetailStatusService - UpdateOrderDetailStatus  to database ]");
        log.info("[End OrderDetailStatusService - UpdateOrderDetailStatus ]");

    }
}
