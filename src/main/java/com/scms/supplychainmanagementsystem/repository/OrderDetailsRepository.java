package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Order;
import com.scms.supplychainmanagementsystem.entity.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    void deleteAllByOrder(Order order);

    @Query(value = "select case when count(o.orderDetailId) > 0 then true else false end" +
            " from OrderDetails o where o.order = :order " +
            " and o.orderDetailsStatus.orderDetailStatusID = 1")
    boolean existsByOrderIdAndNotEnoughStock(Order order);

    @Query(value = "select o from OrderDetails o where o.order.orderId = :orderId " +
            " and :productName is null or o.product.productName like %:productName% " +
            " and :orderDetailsStatusId is null or o.orderDetailsStatus.orderDetailStatusID = :orderDetailsStatusId ")
    Page<OrderDetails> getAllOrderDetailsByOrderId(@Param("orderId") Long orderId,
                                                   @Param("productName") String productName,
                                                   @Param("orderDetailsStatusId") Long orderDetailsStatusId,
                                                   Pageable pageable);

}
