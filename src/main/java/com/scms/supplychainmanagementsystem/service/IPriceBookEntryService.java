package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.PriceBookEntryDto;
import com.scms.supplychainmanagementsystem.entity.PriceBookEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPriceBookEntryService {

    void updatePriceBookEntry(PriceBookEntryDto priceBookEntryDto);

    void createPriceBookEntry(PriceBookEntryDto priceBookEntryDto);

    void deletePriceBookEntryById(Long priceBookEntryId);

    PriceBookEntryDto getPriceBookEntryById(Long priceBookEntryId);

    List<PriceBookEntryDto> getAllPriceBookEntriesByProductId(Long productId);

    Page<PriceBookEntry> getAllPriceBookEntriesByPriceBookId(Long priceBookId, Pageable pageable);
}
