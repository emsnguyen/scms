package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.InventoryDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.*;
import com.scms.supplychainmanagementsystem.service.IInventoryService;
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
public class InventoryService implements IInventoryService {
    private InventoryRepository inventoryRepository;
    private final UserCommon userCommon;
    private WarehouseRepository warehouseRepository;
    private ProductRepository productRepository;
    private StockService stockService;
    private StockRepository stockRepository;
    private InvProductStatusRepository invProductStatusRepository;

    @Override
    public Page<Inventory> getAllInventory(String Productname, Long warehouseId, Pageable pageable) {
        log.info("[Start PurchaseService - Get All Purchase]");
        Page<Inventory> inventoryPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            inventoryPage = inventoryRepository.filterAllWarehouses(warehouseId,Productname, pageable);
        } else {
            inventoryPage = inventoryRepository.filterInOneWarehouse(wh.getWarehouseID(),Productname, pageable);
        }
        log.info("[End PurchaseService - Get All Purchase]");
        return inventoryPage;
    }

    @Override
    public Inventory getInventoryByIdInWarehouse(Long InventoryId) {
        User currentUser = userCommon.getCurrentUser();
        Inventory inventory = new Inventory();
        if (currentUser.getRole().getRoleID() != 1) {
            inventory = inventoryRepository.findByinventoryIdAnhInWarehouse(InventoryId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
        } else {
            inventory = inventoryRepository.getById(InventoryId);
        }
        return inventory;
    }

    @Override
    public void updateInventory(Long inventoryId, InventoryDto inventoryDto) {
        log.info("[Start InventoryService - UpdateInventory  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");


        Double QuantityOld=inventoryRepository.getById(inventoryId).getShortageQuantity();  //10
        if (currentUser.getRole().getRoleID() != 1) {
            if (currentUser.getWarehouse().getWarehouseID() != inventoryRepository.getById(inventoryId).getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        }
 //                        15                 -   10 =5 > 10
        if(inventoryDto.getShortageQuantity()-QuantityOld>stockRepository.findByProduct(productRepository.getById(inventoryDto.getProductId())).getAvailableQuantity()){
            throw new AppException("Số Lượng thiếu hụt vượt quá số lượng trong kho! ");
        }

        Product product= productRepository.getById(inventoryDto.getProductId());
        InvProductStatus invProductStatus = invProductStatusRepository.getById(inventoryDto.getStatusId());
        Inventory inventory = Inventory.builder()
                .inventoryId(inventoryId)
                .description(inventoryDto.getDescription())
                .personCheck(currentUser.getUsername())
                .shortageQuantity(inventoryDto.getShortageQuantity())
                .status(invProductStatus)
                .product(product)
                .lastModifiedDate(Instant.now())
                .createdDate(Instant.now())
                .dateCheck(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .warehouse(warehouseRepository.getById(inventoryDto.getWarehouseId()))
                .build();
        log.info("[Start update InventoryService  to database]");
        inventoryRepository.save(inventory);
        stockService.updateStockQuantity(product.getProductId(),-(inventoryDto.getShortageQuantity()-QuantityOld));
        log.info("[Start InventoryService - UpdateInventory  to database ]");
        log.info("[End InventoryService - UpdateInventory ]");
    }

    @Override
    public void saveInventory(InventoryDto inventoryDto) {
        log.info("[Start InventoryService - saveInventory  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        if(inventoryDto.getShortageQuantity()>stockRepository.findByProduct(productRepository.getById(inventoryDto.getProductId())).getAvailableQuantity()){
            throw new AppException("Số Lượng thiếu hụt vượt quá số lượng trong kho! ");
        }
        Warehouse warehouse = new Warehouse();
        if(currentUser.getRole().getRoleID()==1){
            warehouse= warehouseRepository.getById(inventoryDto.getWarehouseId());
        }else{
            warehouse=currentUser.getWarehouse();
        }

        Product product= productRepository.getById(inventoryDto.getProductId());
        InvProductStatus invProductStatus = invProductStatusRepository.getById(inventoryDto.getStatusId());
        Inventory inventory = Inventory.builder()
                .description(inventoryDto.getDescription())
                .personCheck(currentUser.getUsername())
                .shortageQuantity(inventoryDto.getShortageQuantity())
                .status(invProductStatus)
                .product(product)
                .lastModifiedDate(Instant.now())
                .createdDate(Instant.now())
                .dateCheck(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .warehouse(warehouse)
                .build();
        log.info("[Start save InventoryService  to database]");
        inventoryRepository.saveAndFlush(inventory);
        stockService.updateStockQuantity(product.getProductId(),-inventoryDto.getShortageQuantity());
        log.info("[Start InventoryService - saveInventory  to database ]");
        log.info("[End InventoryService - saveInventory ]");
    }

    @Override
    public void deleteInventory(Long InventoryId) {
        Double quantityOld=inventoryRepository.getById(InventoryId).getShortageQuantity();
        Long productId = inventoryRepository.getById(InventoryId).getProduct().getProductId();
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            if(currentUser.getWarehouse().getWarehouseID()!=inventoryRepository.getById(InventoryId).getWarehouse().getWarehouseID()) {
                throw new AppException("you cant delete in another Warehouse"); }

                inventoryRepository.deleteinventory(InventoryId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
                stockService.updateStockQuantity(productId,quantityOld);}
        else {
                inventoryRepository.deleteinventoryAdmin(InventoryId);
                stockService.updateStockQuantity(productId,quantityOld);}
        }
        }

