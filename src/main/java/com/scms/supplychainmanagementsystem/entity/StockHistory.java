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
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockHistoryID;

    private Double stockInQuantity;

    private Double unitCostPrice;

    private Instant createdDate;

    private Instant lastModifiedDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    private Product product;


    @ManyToOne
    @JoinColumn(name = "CreatedBy", referencedColumnName = "CreatedBy")
    private Product createdBy;

    @ManyToOne
    @JoinColumn(name = "LastModifiedBy", referencedColumnName = "LastModifiedBy")
    private Product lastModifiedBy;

}
