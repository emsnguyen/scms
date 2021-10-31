package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.InvoiceDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.*;
import com.scms.supplychainmanagementsystem.service.IInvoiceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class InvoiceService implements IInvoiceService {
    private final UserCommon userCommon;
    private WarehouseRepository warehouseRepository;
    private InvoiceRepository invoiceRepository;
    private OrderRepository orderRepository;


    @Override
    public Page<Invoice> getAllInvoice(String invoiceCode, Long warehouseId, Pageable pageable) {
        log.info("[Start InvoiceService - Get All Invoice]");
        Page<Invoice> invoicePage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            invoicePage = invoiceRepository.filterAllWarehouses(warehouseId,invoiceCode, pageable);
        } else {
            invoicePage = invoiceRepository.filterInOneWarehouse(wh.getWarehouseID(),invoiceCode, pageable);
        }
        log.info("[End InvoiceService - Get All Invoice]");
        return invoicePage;
    }

    @Override
    public Invoice getInvoiceByIdInWarehouse(Long invoiceId) {
        User currentUser = userCommon.getCurrentUser();
        Invoice invoice = new Invoice();
        if (currentUser.getRole().getRoleID() != 1) {
            invoice = invoiceRepository.findByinvoiceIdAnhInWarehouse(invoiceId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
        } else {
            invoice = invoiceRepository.getById(invoiceId);
        }
        return invoice;
    }

    @Override
    public void updateInvoice(Long invoiceId, InvoiceDto invoiceDto) {
        log.info("[Start InvoiceService - UpdateInvoice  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        if (currentUser.getRole().getRoleID() != 1) {
            if (currentUser.getWarehouse().getWarehouseID() != invoiceRepository.getById(invoiceId).getOrder().getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        }

        Invoice invoice = Invoice.builder()
                .invoiceId(invoiceId)
                .totalAmount(invoiceDto.getTotalAmount())
                .paymentDueDate(invoiceDto.getPaymentDueDate())
                .totalAmountPaid(invoiceDto.getTotalAmountPaid())
                .invoiceCode("")
                .order(orderRepository.getById(invoiceDto.getOrderId()))
                .lastModifiedDate(Instant.now())
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start update InvoiceService  to database]");
        invoiceRepository.save(invoice);
        log.info("[Start InvoiceService - updateInvoice   to database ]");
        log.info("[End InvoiceService - updateInvoice ]");
    }

    @Override
    public void saveInvoice(InvoiceDto invoiceDto) {
        log.info("[Start InvoiceService - saveInvoice  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Invoice invoice = Invoice.builder()
                .totalAmount(invoiceDto.getTotalAmount())
                .paymentDueDate(invoiceDto.getPaymentDueDate())
                .totalAmountPaid(invoiceDto.getTotalAmountPaid())
                .invoiceCode("")
                .order(orderRepository.getById(invoiceDto.getOrderId()))
                .lastModifiedDate(Instant.now())
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start save InvoiceService  to database]");
        invoiceRepository.saveAndFlush(invoice);
        log.info("[Start InvoiceService - saveInvoice   to database ]");
        log.info("[End InvoiceService - saveInvoice ]");
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            if(currentUser.getWarehouse().getWarehouseID()!=invoiceRepository.getById(invoiceId).getOrder().getWarehouse().getWarehouseID()) {
                throw new AppException("you cant delete in another Warehouse"); }
            invoiceRepository.deleteinvoice(invoiceId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
          }
        else {
            invoiceRepository.deleteinvoiceAdmin(invoiceId);
          }
    }
}
