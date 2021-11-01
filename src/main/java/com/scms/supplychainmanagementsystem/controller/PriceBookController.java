package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PriceBookDto;
import com.scms.supplychainmanagementsystem.entity.PriceBook;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.service.IPriceBookService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

@RestController
@RequestMapping("/api/pricebook")
@AllArgsConstructor
@Slf4j
public class PriceBookController {
    private final IPriceBookService iPriceBookService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Not required priceBookId, just required warehouseID for admin ")
    public ResponseEntity<Map<String, Object>> createPriceBook(@RequestBody PriceBookDto priceBookDto) {
        log.info("[Start PriceBookController -  createPriceBook " + priceBookDto.getPriceBookName() + "]");
        Map<String, Object> result = new HashMap<>();
        iPriceBookService.createPriceBook(priceBookDto);
        result.put("message", "Price Book Created Successfully");
        log.info("[End PriceBookController -  createPriceBook " + priceBookDto.getPriceBookName() + "]");
        return status(CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPriceBooks(@RequestParam(required = false) String priceBookName,
                                                                @RequestParam(required = false) Long warehouseId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        log.info("[Start PriceBookController -  getAllPriceBooks]");
        List<PriceBook> priceBookList;
        Page<PriceBook> priceBookPage;
        Pageable pageable = PageRequest.of(page, size);
        priceBookPage = iPriceBookService.getAllPriceBooks(priceBookName, warehouseId, pageable);

        priceBookList = priceBookPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", priceBookList);
        response.put("currentPage", priceBookPage.getNumber());
        response.put("totalItems", priceBookPage.getTotalElements());
        response.put("totalPages", priceBookPage.getTotalPages());
        if (!priceBookPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End PriceBookController -  getAllPriceBooks]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{priceBookId}")
    public ResponseEntity<Map<String, Object>> getPriceBookById(@PathVariable Long priceBookId) {
        log.info("[Start PriceBookController -  getPriceBookById : " + priceBookId + "]");
        Map<String, Object> result = new HashMap<>();
        PriceBookDto priceBookDto = iPriceBookService.getPriceBookById(priceBookId);
        result.put("data", priceBookDto);
        result.put("message", OK);
        log.info("[End PriceBookController -  getPriceBookById : " + priceBookId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{priceBookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Not required priceBookId, just required warehouseID for admin ")
    public ResponseEntity<Map<String, Object>> updatePriceBook(@PathVariable Long priceBookId, @Valid @RequestBody PriceBookDto priceBookDto) {
        log.info("[Start PriceBookController -  updatePriceBook : " + priceBookId + "]");
        Map<String, Object> result = new HashMap<>();
        priceBookDto.setPriceBookId(priceBookId);
        iPriceBookService.updatePriceBook(priceBookDto);
        result.put("message", "Price Book Updated Successfully");
        log.info("[End PriceBookController -  getPriceBookById : " + priceBookId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{priceBookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<Map<String, Object>> deletePriceBookById(@PathVariable Long priceBookId) {
        log.info("[Start PriceBookController - updatePriceBook  priceBookId = " + priceBookId + "]");
        Map<String, Object> result = new HashMap<>();
        try {
            iPriceBookService.deletePriceBookById(priceBookId);
        } catch (DataIntegrityViolationException e) {
            throw new AppException("Some resource using this data");
        }
        result.put("message", "Price Book Deleted Successfully");
        log.info("[End PriceBookController - updatePriceBook  priceBookId = " + priceBookId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<Map<String, Object>> getAllPriceBookByProducId(@PathVariable Long productId) {
        log.info("[Start PriceBookController -  getAllPriceBookByProducId : " + productId + "]");
        Map<String, Object> result = new HashMap<>();
        List<PriceBookDto> priceBookDtoList = iPriceBookService.getAllPriceBookByProducId(productId);
        result.put("data", priceBookDtoList);
        result.put("message", OK);
        log.info("[End PriceBookController -  getAllPriceBookByProducId : " + productId + "]");
        return status(HttpStatus.OK).body(result);
    }
}
