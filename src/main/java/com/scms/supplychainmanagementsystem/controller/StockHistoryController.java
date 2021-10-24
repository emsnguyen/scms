package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PurchaseDto;
import com.scms.supplychainmanagementsystem.dto.StockDto;
import com.scms.supplychainmanagementsystem.dto.StockHistoryDto;
import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.StockHistory;
import com.scms.supplychainmanagementsystem.service.IStockHistoryService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/manage/stockhistory")
public class StockHistoryController {
    private IStockHistoryService iStockHistoryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createStockHistory(@Valid @RequestBody StockHistoryDto stockHistoryDto) {
        log.info("[Start PurchaseController -  createPurchase  ]");
        iStockHistoryService.saveStockHistory(stockHistoryDto);
        log.info("[End PurchaseController -  createPurchase  ]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/product/{productid}")
    public ResponseEntity<Map<String, Object>> getAllStockHistory(@PathVariable Long productid,
                                                              @RequestParam(required = false) Long warehouseId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("[Start StockHistoryController - Get All StockHistory In Warehouse]");
        List<StockHistory> StockHistorylList;
        Page<StockHistory> StockHistoryPage;
        Pageable pageable = PageRequest.of(page, size);

        StockHistoryPage = iStockHistoryService.getAllStockHistory(productid,warehouseId, pageable);

        StockHistorylList = StockHistoryPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", StockHistorylList);
        response.put("currentPage", StockHistoryPage.getNumber());
        response.put("totalItems", StockHistoryPage.getTotalElements());
        response.put("totalPages", StockHistoryPage.getTotalPages());
        if (!StockHistoryPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End StockHistoryController - Get All StockHistory In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{stockhistoryid}")
    public ResponseEntity<StockHistory> getStockHistoryById(@PathVariable Long stockhistoryid) {
        log.info("[Start StockHistoryController - Get StockHistory By ID]");
        StockHistory stockHistory = iStockHistoryService.getStockHistoryByIdInWarehouse(stockhistoryid);
        if (stockHistory != null) {
            StockHistoryDto stockHistoryDto = new StockHistoryDto(stockHistory);
            log.info("[End StockHistoryController - Get StockHistory By ID]");
            return status(HttpStatus.OK).body(stockHistory);
        } else {
            return null;
        }
    }

    @PutMapping("/{stockhistoryid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateStockHistory(@PathVariable Long stockhistoryid, @Valid @RequestBody StockHistoryDto stockHistoryDto) {
        log.info("[Start StockHistoryController - Update StockHistory ]");
        iStockHistoryService.updateStockHistory(stockhistoryid, stockHistoryDto);
        log.info("[End StockHistoryController - Update StockHistory ]");
        return new ResponseEntity<>("Update StockHistory Successfully", OK);
    }

    @DeleteMapping("/{stockhistoryid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteStockHistory(@PathVariable Long stockhistoryid) {
        log.info("[Start StockHistoryController - Delete StockHistory By ID]");
        iStockHistoryService.deleteStockHistory(stockhistoryid);
        log.info("[End StockHistoryController - Delete StockHistory By ID]");
        return new ResponseEntity<>("Delete StockHistory Successfully", OK);
    }
}
