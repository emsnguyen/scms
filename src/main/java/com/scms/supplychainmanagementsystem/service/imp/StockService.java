package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.StockDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.*;
import com.scms.supplychainmanagementsystem.service.IStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class StockService implements IStockService {

    private final UserCommon userCommon;
    private StockRepository stockRepository;
    private ProductRepository productRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private OrderDetailStatus orderDetailStatus;
    private OrderDetailsService orderDetailsService;


    @Override
    public Page<Stock> getAllStock(Long productId, Long warehouseId,Pageable pageable) {
        log.info("[Start PurchaseService - Get All Purchase]");
        Page<Stock> StockPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            StockPage = stockRepository.filterAllWarehouses(productId,warehouseId, pageable);
        } else {
            StockPage = stockRepository.filterInOneWarehouse(productId,wh.getWarehouseID(), pageable);
        }
        log.info("[End PurchaseService - Get All Purchase]");
        return StockPage;

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

        if (currentUser.getRole().getRoleID() != 1) {
            if (currentUser.getWarehouse().getWarehouseID() != stockRepository.findByStockIdId(stockId).getProduct().getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        }

        Product product = new Product();
        product=productRepository.getById(stockDto.getProductId());
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


    public void updateStockQuantity(Long productId, Double quantity) {

        Stock strock = stockRepository.findByProductId(productId);
        Double sumQuantity=strock.getAvailableQuantity()+quantity;
        Product product =productRepository.getById(productId);
        if(quantity>0 ){

            List<OrderDetails> list =orderDetailsRepository.getOrderDetailsByProductId(productId);

            for (OrderDetails orderDetails : list ) {
                if(orderDetails.getOrderDetailsStatus().getOrderDetailStatusID()==1 && sumQuantity>=orderDetails.getQuantity()){
                    orderDetailsService.updateStatusOrderDetails(orderDetails.getOrderDetailId(),2L);
                    sumQuantity-=orderDetails.getQuantity();
                }
            }
        }
        Stock stock = Stock.builder()
                .stockId(strock.getStockId())
                .availableQuantity(sumQuantity)
                .lastModifiedDate(Instant.now())
                .product(product)
                .build();
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
        product = productRepository.getById(stockDto.getProductId());

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
            stockRepository.deleteStockAdmin(stockId);
        }
    }


