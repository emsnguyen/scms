package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PriceBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceBookRepository extends JpaRepository<PriceBook, Long> {
    boolean existsByIsStandardPriceBook(Boolean isActive);

    @Query(value = "select p from PriceBook p where p.warehouse.warehouseID =:warehouseId " +
            " and (:priceBookName is null or p.priceBookName like %:priceBookName%) " +
            " order by p.priceBookId desc")
    Page<PriceBook> filterInOneWarehouse(@Param("priceBookName") String priceBookName,
                                         @Param("warehouseId") Long warehouseId,
                                         Pageable pageable);

    @Query(value = "select p from PriceBook p where (:warehouseId is null or p.warehouse.warehouseID = :warehouseId) " +
            " and (:priceBookName is null or p.priceBookName like %:priceBookName%) " +
            " order by p.priceBookId desc")
    Page<PriceBook> filterAllWarehouses(@Param("priceBookName") String priceBookName,
                                        @Param("warehouseId") Long warehouseId,
                                        Pageable pageable);

}
