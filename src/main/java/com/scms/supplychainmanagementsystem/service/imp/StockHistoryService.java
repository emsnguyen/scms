package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.StockHistoryDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.PurchaseDetailRepository;
import com.scms.supplychainmanagementsystem.repository.StockHistoryRepository;
import com.scms.supplychainmanagementsystem.repository.StockRepository;
import com.scms.supplychainmanagementsystem.service.IStockHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class StockHistoryService implements IStockHistoryService {
    private final UserCommon userCommon;
    private StockHistoryRepository stockHistoryRepository;
    private StockService stockService;

    @Override
    public Page<StockHistory> getAllStockHistory(Long productid,Long warehouseId, Pageable pageable) {
        log.info("[Start PurchaseService - Get All Purchase]");
        Page<StockHistory> stockHistoryPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            stockHistoryPage = stockHistoryRepository.filterAllWarehouses(productid,warehouseId, pageable);
        } else {
            stockHistoryPage = stockHistoryRepository.filterInOneWarehouse(productid,wh.getWarehouseID(), pageable);
        }
        log.info("[End PurchaseService - Get All Purchase]");
        return stockHistoryPage;
    }

    @Override
    public StockHistory getStockHistoryByIdInWarehouse(Long stockHistoryId) {
        User currentUser = userCommon.getCurrentUser();
        StockHistory stockHistory = new StockHistory();
        if (currentUser.getRole().getRoleID() != 1) {
            stockHistory = stockHistoryRepository.findByStockHistoryId(stockHistoryId, currentUser.getWarehouse().getWarehouseID());
        } else {
            stockHistory = stockHistoryRepository.findByStockHistoryIdAdmin(stockHistoryId);
        }
        return stockHistory;
    }

    @Override
    public void updateStockHistory(Long stockHistoryId, StockHistoryDto stockHistoryDto) {
        log.info("[Start PurchaseService - UpdatePurchase  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Product product = new Product();
        product.setProductId(stockHistoryDto.getProductId());

        if (currentUser.getRole().getRoleID() != 1) {
            if (currentUser.getWarehouse().getWarehouseID() != stockHistoryRepository.findByStockHistoryIdAdmin(stockHistoryId).getProduct().getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        }

        StockHistory stockHistory = StockHistory.builder()
                .stockHistoryID(stockHistoryId)
                .createdDate(Instant.now())
                .stockInQuantity(stockHistoryDto.getStockInQuantity())
                .unitCostPrice(stockHistoryDto.getUnitCostPrice())
                .lastModifiedDate(Instant.now())
                .product(product)
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start Update PurchaseService  to database]");
        stockHistoryRepository.save(stockHistory);
        log.info("[Start PurchaseService - UpdatePurchase  to database ]");
        log.info("[End PurchaseService -UpdatePurchase ]");
    }

    @Override
    public void saveStockHistory(StockHistoryDto stockHistoryDto) {
        log.info("[Start StockHistoryService - saveStockHistory  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Product product = new Product();
        product.setProductId(stockHistoryDto.getProductId());

        StockHistory stockHistory = StockHistory.builder()
                .createdDate(Instant.now())
                .stockInQuantity(stockHistoryDto.getStockInQuantity())
                .unitCostPrice(stockHistoryDto.getUnitCostPrice())
                .lastModifiedDate(Instant.now())
                .product(product)
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start save PurchaseService  to database]");
        stockHistoryRepository.saveAndFlush(stockHistory);
        stockService.updateStockQuantity(product.getProductId(),stockHistoryDto.getStockInQuantity());
        log.info("[Start PurchaseService - savePurchase  to database ]");
        log.info("[End PurchaseService - savePurchase ]");
    }

    @Override
    public void deleteStockHistory(Long stockHistoryId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            stockHistoryRepository.deleteStockHistory(stockHistoryId, currentUser.getWarehouse().getWarehouseID());
        } else {
            stockHistoryRepository.deleteStockHistoryAdmin(stockHistoryId);
        }
    }
    }

