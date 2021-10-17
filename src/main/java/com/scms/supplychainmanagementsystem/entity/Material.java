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
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialID;


    private String MaterialName;

    private String quantityUnitOfMeasure ;

    private Instant createdDate ;

    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseID")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;


}
