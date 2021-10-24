package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Product;
import com.scms.supplychainmanagementsystem.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "select case when s.availableQuantity > 0 then true else false end" +
            " from Stock s where s.product.productId = :productId")
    boolean checkQtyAvailablePositive(Long productId);

    Stock findByProduct(Product product);
}
