package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private Product product;

    private Double quantity;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "priceBookId", referencedColumnName = "priceBookId")
    private PriceBook priceBook;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "orderDetailStatusId", referencedColumnName = "orderDetailStatusId")
    private OrderDetailsStatus orderDetailsStatus;
}
