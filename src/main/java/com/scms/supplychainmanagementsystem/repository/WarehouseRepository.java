package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {


    void deleteWarehouseByWarehouseID(Long warehouseId);

    boolean existsByWarehouseName(String warehouseName);

    @Query(value = "select u from Warehouse u where  (:warehouseName is null or u.warehouseName like %:warehouseName%) " +
            "and   (:adress is null or u.address like %:adress%) ")
    Page<Warehouse> filterAll(@Param("warehouseName") String warehouseName,@Param("adress") String adress, Pageable pageable);

}
