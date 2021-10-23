package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.PurchaseDto;
import com.scms.supplychainmanagementsystem.dto.StockHistoryDto;
import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.StockHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStockHistoryService {

    Page<StockHistory> getAllStockHistory(Long warehouseId, Pageable pageble);

    StockHistory getStockHistoryByIdInWarehouse(Long stockHistoryId);

    void updateStockHistory(Long stockHistoryId, StockHistoryDto stockHistoryDto);

    void saveStockHistory(StockHistoryDto stockHistoryDto);

    void deleteStockHistory(Long stockHistoryId);
}
