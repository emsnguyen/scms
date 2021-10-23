package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.PurchaseDetailDto;
import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPurchaseDetailService {
    Page<PurchaseDetails> getAllPurchaseDetail(Long purchaseid, Long warehouseId, Pageable pageble);

    PurchaseDetails getPurchaseDetailByIdInWarehouse(Long purchaseDetailId);

    void updatePurchaseDetail(Long purchasedDetailId, PurchaseDetailDto purchaseDetailDto);

    void savePurchaseDetail(PurchaseDetailDto purchaseDetailDtoDto);

    void deletePurchaseDetail(Long purchaseDetailId);

}
