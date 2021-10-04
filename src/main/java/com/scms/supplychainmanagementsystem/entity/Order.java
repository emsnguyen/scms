package com.scms.supplychainmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

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
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "ContactID", referencedColumnName = "ContactID")
    private ContactDelivery contact;


    @ManyToOne
    @JoinColumn(name = "OrderStatusID", referencedColumnName = "orderStatusID")
    private OrderStatus orderStatus;


    @ManyToOne
    @JoinColumn(name = "CreatedBy", referencedColumnName = "userId")
    private User createdBy;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userId")
    private User lastModifiedBy;


}
