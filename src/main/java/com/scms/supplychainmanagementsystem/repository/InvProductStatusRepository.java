package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.InvProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvProductStatusRepository extends JpaRepository<InvProductStatus ,Long> {
}
