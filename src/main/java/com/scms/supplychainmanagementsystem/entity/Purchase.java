package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseID;

    private LocalDate purchaseDate;

    private Instant createdDate;

    private Instant lastModifiedDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "SupplierID", referencedColumnName = "SupplierID")
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseID")
    private Warehouse warehouse;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;

}
