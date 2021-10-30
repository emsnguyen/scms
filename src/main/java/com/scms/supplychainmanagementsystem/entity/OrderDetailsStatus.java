package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderDetailsStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailStatusID;

    private String status;
}
