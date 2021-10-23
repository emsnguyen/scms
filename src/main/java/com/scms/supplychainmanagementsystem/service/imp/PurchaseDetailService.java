package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.PurchaseDetailDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.repository.PurchaseDetailRepository;
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

    private final UserCommon userCommon;
    private PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public Page<PurchaseDetails> getAllPurchaseDetail(Long purchaseid, Long warehouseId, Pageable pageable) {
        log.info("[Start PurchaseDetailService - Get All PurchaseDetail]");
        Page<PurchaseDetails> PurchasePage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();


        if (current.getRole().getRoleID() == 1) {
            PurchasePage = purchaseDetailRepository.filterAllWarehouses(purchaseid, warehouseId, pageable);
        } else {
            PurchasePage = purchaseDetailRepository.filterInOneWarehouse(purchaseid, wh.getWarehouseID(), pageable);
        }
        log.info("[End PurchaseDetailService - Get All PurchaseDetail]");
        return PurchasePage;
    }

    @Override
    public PurchaseDetails getPurchaseDetailByIdInWarehouse(Long purchaseDetailId) {
        User currentUser = userCommon.getCurrentUser();
        PurchaseDetails purchaseDetails = new PurchaseDetails();
        if (currentUser.getRole().getRoleID() != 1) {
            purchaseDetails = purchaseDetailRepository.findByPurchaseDetailId(purchaseDetailId, currentUser.getWarehouse().getWarehouseID());
        } else {
            purchaseDetails = purchaseDetailRepository.findByPurchaseDetailIdAdmin(purchaseDetailId);
        }
        return purchaseDetails;
    }

    @Override
    public void updatePurchaseDetail(Long purchasedDetailId, PurchaseDetailDto purchaseDetailDto) {
        log.info("[Start PurchaseDetailService - UpdatePurchaseDetail  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");


        Material material = new Material();
        material.setMaterialID(purchaseDetailDto.getMaterialId());
        Purchase purchase = new Purchase();
        purchase.setPurchaseID(purchaseDetailDto.getPurchaseId());


        PurchaseDetails purchaseDetails = PurchaseDetails.builder()
                .purchaseDetailID(purchasedDetailId)
                .material(material)
                .purchase(purchase)
                .quantity(purchaseDetailDto.getQuantity())
                .unitPrice(purchaseDetailDto.getUnitPrice())
                .build();
        log.info("[Start Update PurchaseDetailService  to database]");
        purchaseDetailRepository.save(purchaseDetails);
        log.info("[Start PurchaseService - UpdatePurchaseDetail  to database ]");
        log.info("[End PurchaseDetailService -UpdatePurchaseDetail ]");
    }

    @Override
    public void savePurchaseDetail(PurchaseDetailDto purchaseDetailDto) {
        log.info("[Start PurchaseDetailService - savePurchaseDetail  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Material material = new Material();
        material.setMaterialID(purchaseDetailDto.getMaterialId());
        Purchase purchase = new Purchase();
        purchase.setPurchaseID(purchaseDetailDto.getPurchaseId());


//         Warehouse warehouse = new Warehouse();
//
//        if (currentUser.getRole().getRoleID() != 1) {
//            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());
//        } else {
//            warehouse.setWarehouseID(purchaseDto.getWarehouseId());
//        }

        PurchaseDetails purchaseDetails = PurchaseDetails.builder()
                .material(material)
                .purchase(purchase)
                .quantity(purchaseDetailDto.getQuantity())
                .unitPrice(purchaseDetailDto.getUnitPrice())
                .build();
        log.info("[Start save PurchaseDetailService  to database]");
        purchaseDetailRepository.saveAndFlush(purchaseDetails);
        log.info("[Start PurchaseDetailService - savePurchaseDetail  to database ]");
        log.info("[End PPurchaseDetailService - savePurchaseDetail ]");
    }

    @Override
    public void deletePurchaseDetail(Long purchaseDetailId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            purchaseDetailRepository.deletePurchaseDetail(purchaseDetailId, currentUser.getWarehouse().getWarehouseID());
        } else {
            purchaseDetailRepository.deletePurchaseAdmin(purchaseDetailId);
        }
    }
}

