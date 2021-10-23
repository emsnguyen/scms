package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PurchaseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseDetailID;

    private String unitPrice;

    private Double quantity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PurchaseID", referencedColumnName = "PurchaseID")
    private Purchase purchase;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "MaterialId", referencedColumnName = "MaterialId")
    private Material material;

}
