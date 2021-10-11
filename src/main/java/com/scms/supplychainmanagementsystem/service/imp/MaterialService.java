package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.MaterialRepository;
import com.scms.supplychainmanagementsystem.service.IMaterialService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class MaterialService implements IMaterialService {

    private MaterialRepository materialRepository;
    private final UserCommon userCommon;

    @Override
    public Material getMaterialByIdInWarehouse(Long MaterialId) {
        Material material = materialRepository.findByMaterialIdAnhInWarehouse(MaterialId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
        return material;
    }

    @Override
    public void updateMaterial(Long materialId, MaterialDto materialDto) {
        log.info("[Start MaterialService - saveMaterial with MaterialName: " + materialDto.getMaterialName() + "]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());

        Material material = Material.builder()
                .materialID(materialId)
                .MaterialName(materialDto.getMaterialName())
                .quantityUnitOfMeasure(materialDto.getQuantityUnitOfMeasure())
                .warehouse(warehouse)
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start update material " + material.getMaterialName() + " to database]");
        materialRepository.save(material);
        log.info("[End update material " + material.getMaterialName() + " to database]");
        log.info("[End MaterialService - updateMaterial with name: " + material.getMaterialName() + "]");
    }

    @Override
    public void saveMaterial(MaterialDto materialDto) {
        log.info("[Start MaterialService - saveMaterial with MaterialName: " + materialDto.getMaterialName() + "]");
//        if (materialRepository.existsByMaterialName(materialDto.getMaterialName())) {
//            throw new AppException("name exists");
//        }
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());

        Material material = Material.builder()
                .materialID(materialDto.getMaterialID())
                .MaterialName(materialDto.getMaterialName())
                .quantityUnitOfMeasure(materialDto.getQuantityUnitOfMeasure())
                .warehouse(warehouse)
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start save Material " + materialDto.getMaterialName() + " to database]");
        materialRepository.saveAndFlush(material);
        log.info("[End save Material " + materialDto.getMaterialName() + " to database]");
        log.info("[End MaterialService - saveMaterial with MaterialName: " + materialDto.getMaterialName() + "]");
    }

    @Override
    public void deleteMaterial(Long materialId) {
        materialRepository.deleteMaterial(materialId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
    }

    @Override
    public Page<Material> getAllMaterial(String materialname, Long warehouseId, Pageable pageable) {
        log.info("[Start CustomerService - Get All Customer]");
        Page<Material> materialPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            materialPage = materialRepository.filterAllWarehouses(materialname, warehouseId, pageable);
        } else {
            materialPage = materialRepository.filterInOneWarehouse(materialname, wh.getWarehouseID(), pageable);
        }
        log.info("[End CustomerService - Get All Customer]");
        return materialPage;
    }
}
