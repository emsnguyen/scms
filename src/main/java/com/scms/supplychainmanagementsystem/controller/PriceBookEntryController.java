package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PriceBookEntryDto;
import com.scms.supplychainmanagementsystem.entity.PriceBookEntry;
import com.scms.supplychainmanagementsystem.service.IPriceBookEntryService;
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

@RestController
@RequestMapping("/api/pricebook-entry")
@AllArgsConstructor
@Slf4j
public class PriceBookEntryController {

    private final IPriceBookEntryService iPriceBookEntryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Not required priceBookEntryId")
    public ResponseEntity<Map<String, Object>> createPriceBookEntry(@RequestBody PriceBookEntryDto priceBookEntryDto) {
        log.info("[Start PriceBookEntryController -  createPriceBookEntry ]");
        Map<String, Object> result = new HashMap<>();
        iPriceBookEntryService.createPriceBookEntry(priceBookEntryDto);
        result.put("message", "PriceBook Entry Created Successfully");
        log.info("[End PriceBookEntryController -  createPriceBookEntry ]");
        return status(CREATED).body(result);
    }


    @GetMapping("/{priceBookEntryId}")
    public ResponseEntity<Map<String, Object>> getPriceBookEntryById(@PathVariable Long priceBookEntryId) {
        log.info("[Start PriceBookEntryController -  getPriceBookEntryById : " + priceBookEntryId + "]");
        Map<String, Object> result = new HashMap<>();
        PriceBookEntryDto priceBookEntryDto = iPriceBookEntryService.getPriceBookEntryById(priceBookEntryId);
        result.put("data", priceBookEntryDto);
        result.put("message", OK);
        log.info("[End PriceBookEntryController -  getPriceBookEntryById : " + priceBookEntryId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{priceBookEntryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<Map<String, Object>> updatePriceBookEntry(@PathVariable Long priceBookEntryId, @Valid @RequestBody PriceBookEntryDto priceBookEntryDto) {
        log.info("[Start PriceBookEntryController -  updatePriceBookEntry : " + priceBookEntryId + "]");
        Map<String, Object> result = new HashMap<>();
        priceBookEntryDto.setPriceBookEntryId(priceBookEntryId);
        iPriceBookEntryService.updatePriceBookEntry(priceBookEntryDto);
        result.put("message", "PriceBook Entry Updated Successfully");
        log.info("[End PriceBookEntryController -  getPriceBookEntryById : " + priceBookEntryId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{priceBookEntryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<Map<String, Object>> deletePriceBookEntryById(@PathVariable Long priceBookEntryId) {
        log.info("[Start PriceBookEntryController - updatePriceBookEntry  priceBookEntryId = " + priceBookEntryId + "]");
        Map<String, Object> result = new HashMap<>();
        iPriceBookEntryService.deletePriceBookEntryById(priceBookEntryId);
        result.put("message", "PriceBook Entry Deleted Successfully");
        log.info("[End PriceBookEntryController - updatePriceBookEntry  priceBookEntryId = " + priceBookEntryId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getPriceBookEntryByProductId(@PathVariable Long productId) {
        log.info("[Start PriceBookEntryController -  getPriceBookEntryByProductId : " + productId + "]");
        Map<String, Object> result = new HashMap<>();
        List<PriceBookEntryDto> priceBookEntryDto = iPriceBookEntryService.getAllPriceBookEntriesByProductId(productId);
        result.put("data", priceBookEntryDto);
        result.put("message", OK);
        log.info("[End PriceBookEntryController -  getPriceBookEntryByProductId : " + productId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @GetMapping("/pricebook/{priceBookId}")
    public ResponseEntity<Map<String, Object>> getPriceBookEntryByPriceBookId(@PathVariable Long priceBookId,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("[Start PriceBookEntryController -  getPriceBookEntryByPriceBookId : " + priceBookId + "]");
        Map<String, Object> result = new HashMap<>();
        List<PriceBookEntry> priceBookEntryList;
        Page<PriceBookEntry> priceBookEntryPage;
        Pageable pageable = PageRequest.of(page, size);
        priceBookEntryPage = iPriceBookEntryService.getAllPriceBookEntriesByPriceBookId(priceBookId, pageable);

        priceBookEntryList = priceBookEntryPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", priceBookEntryList);
        response.put("currentPage", priceBookEntryPage.getNumber());
        response.put("totalItems", priceBookEntryPage.getTotalElements());
        response.put("totalPages", priceBookEntryPage.getTotalPages());
        if (!priceBookEntryPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End PriceBookEntryController -  getPriceBookEntryByPriceBookId : " + priceBookId + "]");
        return status(HttpStatus.OK).body(response);
    }

}
