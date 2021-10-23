package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.PurchaseDetailDto;
import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import com.scms.supplychainmanagementsystem.service.IPurchaseDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class PurchaseDetailService implements IPurchaseDetailService {
    @Override
    public Page<PurchaseDetails> getAllPurchaseDetail(Long warehouseId, Pageable pageble) {
        return null;
    }

    @Override
    public PurchaseDetails getPurchaseDetailByIdInWarehouse(Long purchaseDetailId) {
        return null;
    }

    @Override
    public void updatePurchaseDetail(Long purchasedDetailId, PurchaseDetailDto purchaseDetailDto) {

    }

    @Override
    public void savePurchaseDetail(PurchaseDetailDto purchaseDetailDtoDto) {

    }

    @Override
    public void deletePurchaseDetail(Long purchaseDetailId) {

    }
}
