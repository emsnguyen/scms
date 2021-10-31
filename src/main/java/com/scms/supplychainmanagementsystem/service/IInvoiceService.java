package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.InventoryDto;
import com.scms.supplychainmanagementsystem.dto.InvoiceDto;
import com.scms.supplychainmanagementsystem.entity.Inventory;
import com.scms.supplychainmanagementsystem.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInvoiceService {
    Page<Invoice> getAllInvoice(String invoiceCode, Long warehouseId, Pageable pageble);

    Invoice getInvoiceByIdInWarehouse(Long invoiceId);

    void updateInvoice(Long invoiceId, InvoiceDto invoiceDto);

    void saveInvoice(InvoiceDto invoiceDto);

    void deleteInvoice(Long invoiceId);
}
