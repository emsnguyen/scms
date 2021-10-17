package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.PriceBookDto;
import com.scms.supplychainmanagementsystem.entity.PriceBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPriceBookService {
    void updatePriceBook(PriceBookDto priceBookDto);

    void createPriceBook(PriceBookDto priceBookDto);

    void deletePriceBookById(Long priceBookId);

    PriceBookDto getPriceBookById(Long priceBookId);

    Page<PriceBook> getAllPriceBooks(String priceBookName, Long warehouseId, Pageable pageable);
}
