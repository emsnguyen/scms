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
    @JoinColumn(name = "CreatedBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "LastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;

}
