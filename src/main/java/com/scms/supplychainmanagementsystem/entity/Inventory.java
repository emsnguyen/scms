package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private String personCheck;

    private Instant dateCheck;

    private String description;

    private double shortageQuantity;

    private Instant createdDate;

    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseID")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "StatusId", referencedColumnName = "StatusId")
    private InvProductStatus status;

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;


    @ManyToOne
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;
}
