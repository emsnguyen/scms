package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PriceBook;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceBookRepository extends JpaRepository<PriceBook, Long> {

    @Query(value = "select p from PriceBook p inner join PriceBookEntry e " +
            " where p.priceBookId = e.priceBook.priceBookId " +
            " and e.product.productId = :productId")
    List<PriceBook> getAllByProductId(Long productId);

    @Query(value = "select case when count(p.priceBookId) > 0 then true else false end " +
            " from PriceBook p where p.warehouse = :warehouse and p.isStandardPriceBook = true " +
            " and (:priceBookId is null or p.priceBookId <> :priceBookId)")
    boolean existsStandardPriceBook(Warehouse warehouse, Long priceBookId);

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
