package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;


    private String customerCode;


    private String CustomerType;


    private String customerName;



    @ManyToOne
    @JoinColumn(name = "WarehouseID", referencedColumnName = "WarehouseID")
    private Warehouse warehouse;

    private String email;

    private String phone;

    private LocalDate DateOfBirth;

    private Boolean Gender;

    private String TaxCode;

    private String Facebook;

    @ManyToOne
    @JoinColumn(name = "DistrictID", referencedColumnName = "DistrictID")
    private District district;

    private String streetAddress;

    private String CompanyName;

    private String Note;

    private Instant createdDate;


    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userID")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "lastModifiedBy", referencedColumnName = "userID")
    private User lastModifiedBy;

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerCode='" + customerCode + '\'' +
                ", CustomerType='" + CustomerType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", warehouse=" + warehouse +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", DateOfBirth=" + DateOfBirth +
                ", Gender=" + Gender +
                ", TaxCode='" + TaxCode + '\'' +
                ", Facebook='" + Facebook + '\'' +
                ", district=" + district +
                ", streetAddress='" + streetAddress + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", Note='" + Note + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy=" + createdBy +
                ", lastModifiedBy=" + lastModifiedBy +
                '}';
    }
}
