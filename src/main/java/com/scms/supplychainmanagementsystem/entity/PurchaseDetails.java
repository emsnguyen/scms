package com.scms.supplychainmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
