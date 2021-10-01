package com.scms.supplychainmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;


    private Instant createdDate;

    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseId")
    private Warehouse warehouseId;

    @ManyToOne
    @JoinColumn(name = "ContactID", referencedColumnName = "ContactID")
    private ContactDelivery contactId;


    @ManyToOne
    @JoinColumn(name = "OrderStatusID", referencedColumnName = "orderStatusID")
    private OrderStatus orderStatusId;


    @ManyToOne
    @JoinColumn(name = "CreatedBy", referencedColumnName = "userId")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userId")
    private User lastModifiedBy;


}
