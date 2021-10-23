package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.StockDto;
import com.scms.supplychainmanagementsystem.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStockService {

    Page<Stock> getAllStock(Long productId,Long StockId, Pageable pageble);

    Stock getStockById(Long stockId);

    void updateStock(Long stockId, StockDto stockDto);

    void saveStock(StockDto stockDto);

    void deleteStock(Long stockId);
}
