package com.scms.supplychainmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictResponse {
    private Long districtID;

    private Long provinceID;

    private String districtName;

}
