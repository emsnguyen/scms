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
@Builder
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
