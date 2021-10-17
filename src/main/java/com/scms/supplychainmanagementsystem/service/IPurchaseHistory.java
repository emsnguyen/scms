package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.dto.PurchaseHistoryDto;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.entity.PurchaseHistory;
import com.scms.supplychainmanagementsystem.entity.Supplier;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPurchaseHistory {
    Page<PurchaseHistory> getAllPurchaseHistory(Long warehouseId, Pageable pageble);

    PurchaseHistory getPurchaseHistoryByIdInWarehouse(Long PurchaseHistoryId);

    void updatePurchaseHistory(Long PurchaseHistoryId, PurchaseHistoryDto purchaseHistoryDto);

    void savePurchaseHistory( PurchaseHistoryDto purchaseHistoryDto);

    void deletePurchaseHistory(Long PurchaseHistoryId);
}
