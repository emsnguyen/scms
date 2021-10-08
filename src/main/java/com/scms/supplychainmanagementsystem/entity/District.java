package com.scms.supplychainmanagementsystem.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long districtID;

    private String districtName;

    private String districtType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provinceID", referencedColumnName = "provinceId")
    private Province province;
}
