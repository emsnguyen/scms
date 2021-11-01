package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PriceBookEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceBookEntryRepository extends JpaRepository<PriceBookEntry, Long> {

    @Query(value = "select case when count(p.priceBookEntryID) > 0 then true else false end from PriceBookEntry p " +
            " where p.priceBook.priceBookId = :priceBookId and p.product.productId = :productId")
    boolean existProductInPriceBook(Long productId, Long priceBookId);

    @Query(value = "select p from PriceBookEntry p where p.priceBook.priceBookId = :priceBookId")
    Page<PriceBookEntry> findAllByPriceBookId(@Param("priceBookId") Long priceBookId, Pageable pageable);

    @Query(value = "select p from PriceBookEntry p where p.product.productId = :productId")
    List<PriceBookEntry> findAllByProductId(@Param("productId") Long productId);
}
