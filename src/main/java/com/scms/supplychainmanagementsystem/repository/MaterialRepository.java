package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>  {

//    Optional<Material> findByMaterialName(String materialName);


    @Query(value = "SELECT * FROM test.material where materialid= :materialid and warehouseid= :warehouseid",nativeQuery = true)
    Material findByMaterialIdAnhInWarehouse(@Param("materialid") Long materialId , @Param("warehouseid") Long warehouseId);


//    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
//    Page<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM test.material where materialid= :materialid and warehouseid= :warehouseid",nativeQuery = true)
    void deleteMaterial(@Param("materialid") Long customerId ,@Param("warehouseid") Long warehouseId);

    @Query(value = "select material_name FROM test.material where material_name= :materialName ",nativeQuery = true)
    boolean existsByMaterialName(@Param("materialName")String materialName);

    @Query(value = "select u from Material u where u.warehouse.warehouseID =:warehouseId " +
            " and (:materialname is null or u.MaterialName like %:materialname%) " +
            " order by u.createdDate desc")
    Page<Material> filterInOneWarehouse(@Param("materialname") String materialname,
                                        @Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Material u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " and (:materialname is null or u.MaterialName like %:materialname%) " +
            " order by u.createdDate desc")
    Page<Material> filterAllWarehouses(@Param("materialname") String materialname,
                                       @Param("warehouseId") Long warehouseId, Pageable pageable);
}

