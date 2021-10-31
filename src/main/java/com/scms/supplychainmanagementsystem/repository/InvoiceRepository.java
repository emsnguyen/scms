package com.scms.supplychainmanagementsystem.repository;


import com.scms.supplychainmanagementsystem.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    @Query(value = "SELECT u FROM Invoice  u where u.invoiceId = :invoiceId and u.order.warehouse.warehouseID= :warehouseid")
    Invoice findByinvoiceIdAnhInWarehouse(@Param("invoiceId") Long invoiceId, @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM Invoice  u where u.order.orderId = :orderId and u.order.warehouse.warehouseID= :warehouseid")
    Invoice findByOrderIdAnhInWarehouse(@Param("orderId") Long orderId, @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM Invoice  u where u.order.orderId = :orderId ")
    Invoice findByOrderId(@Param("orderId") Long orderId);

//    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
//    Page<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Invoice u where u.invoiceId= :invoiceId and u.order.warehouse.warehouseID= :warehouseid")
    void deleteinvoice(@Param("invoiceId") Long invoiceId, @Param("warehouseid") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Invoice u where u.invoiceId= :invoiceId ")
    void deleteinvoiceAdmin(@Param("invoiceId") Long invoiceId);


//    @Query(value = "select material_name FROM test.material where material_name= :materialName ",nativeQuery = true)
//    boolean existsByMaterialName(@Param("materialName")String materialName);

    @Query(value = "select u from Invoice u where u.order.warehouse.warehouseID =:warehouseId " +
            " and (:invoiceCode is null or u.invoiceCode like %:invoiceCode%) " +
            " order by u.lastModifiedDate desc")
    Page<Invoice> filterInOneWarehouse(@Param("warehouseId") Long warehouseId,
                                         @Param("invoiceCode") String invoiceCode, Pageable pageable);

    @Query(value = "select u from Invoice u where (:warehouseId is null or u.order.warehouse.warehouseID = :warehouseId) " +
            " and (:invoiceCode is null or u.invoiceCode like %:invoiceCode%) " +
            " order by u.lastModifiedDate desc")
    Page<Invoice> filterAllWarehouses(@Param("warehouseId") Long warehouseId,
                                        @Param("invoiceCode") String invoiceCode,Pageable pageable);
}
