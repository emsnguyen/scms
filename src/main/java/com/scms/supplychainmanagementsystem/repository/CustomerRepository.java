package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> , PagingAndSortingRepository<Customer, Long> {



    Optional<Customer> findByEmail(String email);


    @Query(value = "SELECT * FROM test.customer where customer_id= :customerid and warehouseid= :warehouseid",nativeQuery = true)
    Customer findByCustomerIdAnhInWarehouse(@Param("customerid") Long customerId ,@Param("warehouseid") Long warehouseId);


    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
    List<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId);


    boolean existsByEmail(String email);
}
