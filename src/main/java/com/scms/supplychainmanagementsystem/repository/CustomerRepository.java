package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>  {



    Optional<Customer> findByEmail(String email);


    @Query(value = "SELECT * FROM test.customer where customer_id= :customerid and warehouseid= :warehouseid",nativeQuery = true)
    Customer findByCustomerIdAnhInWarehouse(@Param("customerid") Long customerId ,@Param("warehouseid") Long warehouseId);


    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
    Page<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId,Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM test.customer where customer_id= :customerid and warehouseid= :warehouseid",nativeQuery = true)
    void deleteCustomer(@Param("customerid") Long customerId ,@Param("warehouseid") Long warehouseId);

    boolean existsByEmail(String email);

    @Query(value = "select u from Customer u where u.warehouse.warehouseID =:warehouseId " +
            " and (:customername is null or u.customerName like %:customername%) " +
            " order by u.createdDate desc")
    Page<Customer> filterInOneWarehouse(@Param("customername") String customername,
                                    @Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Customer u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " and (:customername is null or u.customerName like %:customername%) " +
            " order by u.createdDate desc")
    Page<Customer> filterAllWarehouses(@Param("customername") String customername,
                                   @Param("warehouseId") Long warehouseId, Pageable pageable);
}
