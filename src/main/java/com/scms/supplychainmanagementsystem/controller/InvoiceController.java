package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.InventoryDto;
import com.scms.supplychainmanagementsystem.dto.InvoiceDto;
import com.scms.supplychainmanagementsystem.entity.Inventory;
import com.scms.supplychainmanagementsystem.entity.Invoice;
import com.scms.supplychainmanagementsystem.service.IInventoryService;
import com.scms.supplychainmanagementsystem.service.IInvoiceService;
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
@RequestMapping("/api/invoice")
@AllArgsConstructor
@Slf4j
public class InvoiceController {

    private IInvoiceService iInvoiceService;



    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllInvoice(@RequestParam (required = false) String invoiceCode,
                                                               @RequestParam(required = false) Long warehouseId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        log.info("[Start InvoiceController - Get All Invoice In Warehouse]");
        List<Invoice> InvoiceList;
        Page<Invoice> InvoicePage;
        Pageable pageable = PageRequest.of(page, size);

        InvoicePage = iInvoiceService.getAllInvoice(invoiceCode,warehouseId, pageable);

        InvoiceList = InvoicePage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", InvoiceList);
        response.put("currentPage", InvoicePage.getNumber());
        response.put("totalItems", InvoicePage.getTotalElements());
        response.put("totalPages", InvoicePage.getTotalPages());
        if (!InvoicePage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End InvoiceController - Get All Invoice In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long InvoiceId) {
        log.info("[Start InvoiceController - Get Invoice By ID]");
        Invoice invoice = iInvoiceService.getInvoiceByIdInWarehouse(InvoiceId);
        if (invoice != null) {
            InvoiceDto invoiceDto = new InvoiceDto(invoice);
            log.info("[EndInvoiceController - Get Invoice By ID]");
            return status(HttpStatus.OK).body(invoiceDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{invoiceId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateInvoice(@PathVariable Long invoiceId, @Valid @RequestBody InvoiceDto invoiceDto) {
        log.info("[Start InvoiceController - Update Invoice ]");
        iInvoiceService.updateInvoice(invoiceId, invoiceDto);
        log.info("[End InvoiceController - Update Invoice ]");
        return new ResponseEntity<>("Update Invoice Successfully", OK);
    }

    @DeleteMapping("/{invoiceId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteInvoice(@PathVariable Long invoiceId) {
        log.info("[Start InvoiceController - Delete Invoice By ID]");
        iInvoiceService.deleteInvoice(invoiceId);
        log.info("[End InvoiceController - Delete Invoice By ID]");
        return new ResponseEntity<>("Delete Invoice Successfully", OK);
    }
}
