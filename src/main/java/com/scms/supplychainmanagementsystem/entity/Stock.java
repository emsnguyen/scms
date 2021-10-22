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
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    private Product product;

    private Double availableQuantity;

    private Instant lastModifiedDate;

}
