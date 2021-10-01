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
public class OrderDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;


    private Long productId;

    private Double quantity ;


    @ManyToOne
    @JoinColumn(name = "OrderId", referencedColumnName = "OrderId")
    private Order orderId;


    @ManyToOne
    @JoinColumn(name = "priceBookId", referencedColumnName = "priceBookId")
    private PriceBook priceBookId;


}
