package com.scms.supplychainmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`Order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;


    private Instant createdDate;

    private Instant lastModifiedDate;

    private String note;

    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseId")
    private Warehouse warehouse;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ContactID", referencedColumnName = "ContactID")
    private ContactDelivery contactDelivery;


    @ManyToOne
    @JoinColumn(name = "OrderStatusID", referencedColumnName = "orderStatusID")
    private OrderStatus orderStatus;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private User createdBy;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userId")
    private User lastModifiedBy;


}
