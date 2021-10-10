package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select c from Category c where c.warehouse.warehouseID =:warehouseId " +
            " and (:categoryName is null or c.categoryName like %:categoryName%) " +
            " order by c.categoryId desc")
    Page<Category> filterInOneWarehouse(@Param("categoryName") String categoryName,
                                        @Param("warehouseId") Long warehouseId,
                                        Pageable pageable);

    @Query(value = "select c from Category c where (:warehouseId is null or c.warehouse.warehouseID = :warehouseId) " +
            " and (:categoryName is null or c.categoryName like %:categoryName%) " +
            " order by c.categoryId desc")
    Page<Category> filterAllWarehouses(@Param("categoryName") String categoryName,
                                       @Param("warehouseId") Long warehouseId,
                                       Pageable pageable);

}
