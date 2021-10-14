package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.dto.PurchaseHistoryDto;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.entity.PurchaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPurchaseHistory {
    Page<PurchaseHistory> getAllPurchaseHistory(String PurchaseHistoryName, Long warehouseId, Pageable pageble);

    PurchaseHistory getPurchaseHistoryByIdInWarehouse(Long PurchaseHistoryId);

    void updatePurchaseHistory(Long PurchaseHistoryId, PurchaseHistoryDto purchaseHistoryDto);

    void savePurchaseHistory( PurchaseHistoryDto purchaseHistoryDto);

    void deletePurchaseHistory(Long PurchaseHistoryId);
}
