package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDto {

    private Long warehouseID;

    private String warehouseName;

    private String address;

    public WarehouseDto(Warehouse warehouse) {
        this.warehouseID = warehouse.getWarehouseID();
        this.warehouseName = warehouse.getWarehouseName();
        this.address = warehouse.getAddress();
    }
}
