package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

}
