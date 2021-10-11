package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Role;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

}
