package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.IWarehouseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class WarehouseService implements IWarehouseService {

    private final UserCommon userCommon;
    private WarehouseRepository warehouseRepository;

    @Override
    public Page<Warehouse> getAllWarehouse(String warehouseName, String address, Pageable pageble) {
        log.info("[Start WarehouseService - Get All Warehouse]");
        Page<Warehouse> warehousePage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            warehousePage = warehouseRepository.filterAll(warehouseName, address, pageble);
        } else {

            throw new AppException("you are not Admin");
        }
        log.info("[End  WarehouseService - Get All Warehouse]");
        return warehousePage;
    }

    @Override
    public void updateWarehouse(Long warehouseId, WarehouseDto warehouseDto) {
        log.info("[Start WarehouseService - UpdateWarehouse with WarehouseName: " + warehouseDto.getWarehouseName() + "]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        if (currentUser.getRole().getRoleID() == 1) {
            Warehouse warehouse = Warehouse.builder()
                    .warehouseID(warehouseId)
                    .warehouseName(warehouseDto.getWarehouseName())
                    .address(warehouseDto.getAddress())
                    .build();
            log.info("[Start Update Warehouse " + warehouseDto.getWarehouseName() + " to database]");
            warehouseRepository.save(warehouse);
            log.info("[End update Warehouse " + warehouseDto.getWarehouseName() + " to database]");
            log.info("[End WarehouseService - updateWarehouse with WarehouseName: " + warehouseDto.getWarehouseName() + "]");
        } else {
            throw new AppException("you are not Admin");
        }

    }

    @Override
    public void saveWarehouse(WarehouseDto warehouseDto) {
        log.info("[Start WarehouseService - saveWarehouse with WarehouseName: " + warehouseDto.getWarehouseName() + "]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        if (currentUser.getRole().getRoleID() != 1) {
            throw new AppException("you are not Admin");
        } else {
            Warehouse warehouse = Warehouse.builder()
                    .warehouseName(warehouseDto.getWarehouseName())
                    .address(warehouseDto.getAddress())
                    .build();
            log.info("[Start save Warehouse " + warehouseDto.getWarehouseName() + " to database]");
            warehouseRepository.saveAndFlush(warehouse);
            log.info("[End save Warehouse " + warehouseDto.getWarehouseName() + " to database]");
            log.info("[End WarehouseService - saveWarehouse with WarehouseName: " + warehouseDto.getWarehouseName() + "]");
        }

    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() == 1) {
           warehouseRepository.deleteById(warehouseId);
        } else {
            throw new AppException("you are not Admin");
        }
    }
}
