package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;


    private String SupplierCode;

    private String SupplierName ;

    private String email ;

    private String phone ;

    private String streetAddress ;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseID")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "DistrictId", referencedColumnName = "DistrictId")
    private District district;


    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;


}
