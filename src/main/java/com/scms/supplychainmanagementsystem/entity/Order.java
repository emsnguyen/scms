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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderID;


    private Instant createdDate;

    private Instant lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseID")
    private User warehouse;

    @ManyToOne
    @JoinColumn(name = "ContactID", referencedColumnName = "ContactID")
    private ContactDelivery contactId;



    @ManyToOne
    @JoinColumn(name = "OrderStatusID", referencedColumnName = "OrderStatusID")
    private OrderStatus orderStatusId;


    @ManyToOne
    @JoinColumn(name = "CreatedBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;


}
