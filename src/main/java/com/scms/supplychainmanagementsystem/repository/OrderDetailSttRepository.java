package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.OrderDetailsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailSttRepository extends JpaRepository<OrderDetailsStatus, Long> {

}
