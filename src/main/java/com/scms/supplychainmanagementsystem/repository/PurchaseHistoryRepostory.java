package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseHistoryRepostory extends JpaRepository<PurchaseHistory, Long> {
}
