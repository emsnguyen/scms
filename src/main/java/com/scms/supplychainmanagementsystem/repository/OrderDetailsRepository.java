package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query(value = "select o from OrderDetails o where o.order.orderId = :orderId " +
            " and :productName is null or o.product.productName like %:productName% " +
            " and :orderDetailsStatusId is null or o.orderDetailsStatus.orderDetailStatusID = :orderDetailsStatusId ")
    Page<OrderDetails> getAllOrderDetailsByOrderId(@Param("orderId") Long orderId,
                                                   @Param("productName") String productName,
                                                   @Param("orderDetailsStatusId") Long orderDetailsStatusId,
                                                   Pageable pageable);

}