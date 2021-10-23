package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.StockHistoryDto;
import com.scms.supplychainmanagementsystem.entity.StockHistory;
import com.scms.supplychainmanagementsystem.repository.PurchaseDetailRepository;
import com.scms.supplychainmanagementsystem.service.IStockHistoryService;
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
public class StockHistoryService implements IStockHistoryService {
    private final UserCommon userCommon;
    private PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public Page<StockHistory> getAllStockHistory(Long warehouseId, Pageable pageble) {
        return null;
    }

    @Override
    public StockHistory getStockHistoryByIdInWarehouse(Long stockHistoryId) {
        return null;
    }

    @Override
    public void updateStockHistory(Long stockHistoryId, StockHistoryDto stockHistoryDto) {

    }

    @Override
    public void saveStockHistory(StockHistoryDto stockHistoryDto) {

    }

    @Override
    public void deleteStockHistory(Long stockHistoryId) {

    }
}
