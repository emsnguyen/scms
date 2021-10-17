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
public class PriceBookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceBookEntryID;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    private Product product;


    @ManyToOne
    @JoinColumn(name = "PriceBookId", referencedColumnName = "PriceBookId")
    private PriceBook priceBook;


}
