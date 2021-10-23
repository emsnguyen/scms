package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.StockDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.PurchaseRepository;
import com.scms.supplychainmanagementsystem.repository.StockRepository;
import com.scms.supplychainmanagementsystem.service.IStockService;
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
public class StockService implements IStockService {

    private final UserCommon userCommon;
    private StockRepository stockRepository;

    @Override
    public Page<Stock> getAllStock(Long productId, Long StockId, Pageable pageble) {
//        log.info("[Start PurchaseService - Get All Purchase]");
//        Page<Stock> StockPage;
//        User current = userCommon.getCurrentUser();
//        Warehouse wh = current.getWarehouse();
//        Long userId = current.getUserId();
//        if (current.getRole().getRoleID() == 1) {
//            PurchasePage = purchaseRepository.filterAllWarehouses(warehouseId, pageable);
//        } else {
//            PurchasePage = purchaseRepository.filterInOneWarehouse(wh.getWarehouseID(), pageable);
//        }
//        log.info("[End PurchaseService - Get All Purchase]");
//        return PurchasePage;
        return  null;
    }

    @Override
    public Stock getStockById(Long stockId) {
        User currentUser = userCommon.getCurrentUser();
        Stock stock = new Stock();
        if (currentUser.getRole().getRoleID() != 1) {
            stock = stockRepository.findByStockIdInWarehouse(stockId, currentUser.getWarehouse().getWarehouseID());
        } else {
            stock = stockRepository.findByStockIdId(stockId);
        }
        return stock;
    }


    @Override
    public void updateStock(Long stockId, StockDto stockDto) {
        log.info("[Start StockService - UpdateStock  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();

        if (currentUser.getRole().getRoleID() != 1) {
            if (currentUser.getWarehouse().getWarehouseID() != stockRepository.findByStockIdId(stockId).getProduct().getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        }

        Product product = new Product();
        product.setProductId(stockDto.getProductId());

        Stock stock = Stock.builder()
                .stockId(stockId)
                .availableQuantity(stockDto.getAvailableQuantity())
                .lastModifiedDate(Instant.now())
                .product(product)
                .build();
        log.info("[Start Update PurchaseService  to database]");
        stockRepository.save(stock);
        log.info("[Start StockService - UpdateStock  to database ]");
        log.info("[End StockService - UpdateStock ]");
    }

    @Override
    public void saveStock(StockDto stockDto) {
        log.info("[Start StockService - savePurchase  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Product product = new Product();
        product.setProductId(stockDto.getProductId());

        Stock stock = Stock.builder()
               .availableQuantity(stockDto.getAvailableQuantity())
               .lastModifiedDate(Instant.now())
               .product(product)
                .build();
        log.info("[Start save PurchaseService  to database]");
        stockRepository.saveAndFlush(stock);
        log.info("[Start PurchaseService - savePurchase  to database ]");
        log.info("[End PurchaseService - savePurchase ]");
    }

    @Override
    public void deleteStock(Long stockId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            stockRepository.deleteStock(stockId, currentUser.getWarehouse().getWarehouseID());
        } else {
            stockRepository.deleteStockAdmin(stockId);
        }
    }
    }

