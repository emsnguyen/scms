package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Invoice;
import com.scms.supplychainmanagementsystem.entity.Order;
import com.scms.supplychainmanagementsystem.entity.User;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDto {

    private Long invoiceId;

    private String invoiceCode;

    private Double totalAmount;

    private Double totalAmountPaid;

    private LocalDate paymentDueDate;

    private Instant lastModifiedDate;

    private Long orderId;

    private String lastModifiedBy;

    public InvoiceDto(Invoice invoice) {
        this.invoiceId = invoice.getInvoiceId();
        this.invoiceCode = invoice.getInvoiceCode();
        this.totalAmount = invoice.getTotalAmount();
        this.totalAmountPaid = invoice.getTotalAmountPaid();
        this.paymentDueDate = invoice.getPaymentDueDate();
        this.lastModifiedDate = invoice.getLastModifiedDate();
        this.orderId = invoice.getOrder().getOrderId();
        this.lastModifiedBy = invoice.getLastModifiedBy().getUsername();
    }
}
