package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PurchaseDto;
import com.scms.supplychainmanagementsystem.dto.StockDto;
import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.Stock;
import com.scms.supplychainmanagementsystem.service.IStockService;
import com.scms.supplychainmanagementsystem.service.ISupplierService;
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
@RequestMapping("/api/manage/stock")
public class StockController {
    private IStockService iStockService;


    @GetMapping("/product/{productid}")
    public ResponseEntity<Map<String, Object>> getAllPurchase(@PathVariable  Long productid,
                                                              @RequestParam(required = false) Long warehouseId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("[Start PurchaseController - Get All Purchase In Warehouse]");
        List<Stock> StockList;
        Page<Stock> StockPage;
        Pageable pageable = PageRequest.of(page, size);

        StockPage = iStockService.getAllStock(productid,warehouseId, pageable);

        StockList = StockPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", StockList);
        response.put("currentPage", StockPage.getNumber());
        response.put("totalItems", StockPage.getTotalElements());
        response.put("totalPages", StockPage.getTotalPages());
        if (!StockPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End CustomerController - Get All Customer In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createStock(@Valid @RequestBody StockDto stockDto) {
        log.info("[Start PurchaseController -  createPurchase  ]");
        iStockService.saveStock(stockDto);
        log.info("[End PurchaseController -  createPurchase  ]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<StockDto> getStockById(@PathVariable Long stockId) {
        log.info("[Start PurchaseController - Get Purchase By ID]");
        Stock stock = iStockService.getStockById(stockId);
        if (stock != null) {
            StockDto stockDto = new StockDto(stock);
            log.info("[End PurchaseController - Get Purchase By ID]");
            return status(HttpStatus.OK).body(stockDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{stockId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateStock(@PathVariable Long stockId, @Valid @RequestBody StockDto stockDto) {
        log.info("[Start PurchaseController - Update Purchase ]");
        iStockService.updateStock(stockId, stockDto);
        log.info("[End PurchaseController - Update Purchase ]");
        return new ResponseEntity<>("Update Purchase Successfully", OK);
    }

    @DeleteMapping("/{stockId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteStock(@PathVariable Long stockId) {
        log.info("[Start stockController - Delete stock By ID]");
        iStockService.deleteStock(stockId);
        log.info("[End stockController - Delete stock By ID]");
        return new ResponseEntity<>("Delete stock Successfully", OK);
    }
}
