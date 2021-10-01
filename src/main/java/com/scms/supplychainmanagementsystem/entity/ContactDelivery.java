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
public class ContactDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactID;

    @ManyToOne
    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId")
    private Customer customerId;

    private String contactName;


    private String email;


    private String phone;

    private String address;

    private  Instant createedDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID")
    private District districtId;



    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;



}
