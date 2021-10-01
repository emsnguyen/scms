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
public class PriceBookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceEntryID;


    private Double Price ;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId")
    private Product productId;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PriceBookId", referencedColumnName = "PriceBookId")
    private PriceBook priceBookId;


}
