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
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long districtID;

    private String districtName;

    private String districtType;

    @ManyToOne
    @JoinColumn(name = "provinceID", referencedColumnName = "provinceID")
    private Province province;
}
