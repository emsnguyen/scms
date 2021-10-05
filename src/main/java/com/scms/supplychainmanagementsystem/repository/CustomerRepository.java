package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    Optional<Customer> findByUsername(String username);


    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
    List<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId);




//    boolean existsByUsername(String username);
}
