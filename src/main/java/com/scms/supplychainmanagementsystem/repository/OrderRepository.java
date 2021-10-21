package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select o from Order o where o.warehouse.warehouseID =:warehouseId " +
            " and (:orderCode is null or o.orderCode like %:orderCode%) " +
            " and (:customerId is null or o.contactDelivery.customer.customerId = :customerId) " +
            " and (:orderStatusId is null or o.orderStatus.orderStatusID = :orderStatusId) " +
            " order by o.orderId desc")
    Page<Order> filterInOneWarehouse(@Param("orderCode") String orderCode, @Param("customerId") Long customerId,
                                     @Param("orderStatusId") Long orderStatusId, @Param("warehouseId") Long warehouseId,
                                     Pageable pageable);

    @Query(value = "select o from Order o where (:warehouseId is null or o.warehouse.warehouseID = :warehouseId) " +
            " and (:orderCode is null or o.orderCode like %:orderCode%) " +
            " and (:customerId is null or o.contactDelivery.customer.customerId = :customerId) " +
            " and (:orderStatusId is null or o.orderStatus.orderStatusID = :orderStatusId) " +
            " order by o.orderId desc")
    Page<Order> filterAllWarehouses(@Param("orderCode") String orderCode, @Param("customerId") Long customerId,
                                    @Param("orderStatusId") Long orderStatusId, @Param("warehouseId") Long warehouseId,
                                    Pageable pageable);
}
