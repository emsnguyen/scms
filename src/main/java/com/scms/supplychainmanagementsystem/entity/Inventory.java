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
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;


    private Long personCheck;

    private Instant dateCheck ;

    private Boolean isActive;

    private String description;

    private double currentQuantity;

    private double shortageQuantity;

    private Instant createdDate ;

    private Instant lastModifiedDate ;


    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseID")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    private Product product;



    @ManyToOne
    @JoinColumn(name = "StatusId", referencedColumnName = "StatusId")
    private InvProductStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;


}
