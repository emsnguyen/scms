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
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseID;


    private Date PurchaseDate;

    private String UnitPrice ;

    private Double Quantity;

    @ManyToOne
    @JoinColumn(name = "SupplierID", referencedColumnName = "SupplierID")
    private Supplier supplierId;

    @ManyToOne
    @JoinColumn(name = "MaterialId", referencedColumnName = "MaterialId")
    private Material materialId;



    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;


}