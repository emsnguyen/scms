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


import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockHisID;


    private Double StockInQuantity;

    private Double UnitCostPrice ;

    private Double CurrentQuantity ;

    private Instant createdDate;

    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    private Product productId;



    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CreatedBy", referencedColumnName = "CreatedBy")
    private Product createdBy;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "LastModifiedBy", referencedColumnName = "LastModifiedBy")
    private Product lastModifiedBy;


}
