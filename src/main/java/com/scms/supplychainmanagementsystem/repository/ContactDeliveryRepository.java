package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.ContactDelivery;
import com.scms.supplychainmanagementsystem.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ContactDeliveryRepository extends JpaRepository<ContactDelivery, Long> {
    //    Optional<Material> findByMaterialName(String materialName);


    @Query(value = "SELECT * FROM test.contact_delivery where contactid= :contactid ",nativeQuery = true)
    ContactDelivery findByContactIDManager(@Param("contactid") Long contactid );

    @Query(value = "SELECT * FROM test.contact_delivery where contactid= :contactid",nativeQuery = true)
    ContactDelivery findByContactIDAdmin(@Param("contactid") Long contactid);


//    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
//    Page<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM test.contact_delivery where contactid= :contactid ",nativeQuery = true)
    void deleteContactDeliverie(@Param("contactid") Long contactId );


//    @Query(value = "select material_name FROM test.contact_delivery where material_name= :materialName ",nativeQuery = true)
//    boolean existsByMaterialName(@Param("materialName")String materialName);

    @Query(value = "select u from ContactDelivery u where u.customer.customerId =:customerid " +
            " and (:contactName is null or u.contactName like %:contactName%) " +
            " order by u.createedDate desc")
    Page<ContactDelivery> filterInOneCustomer(@Param("customerid") Long customerid,
                                        @Param("contactName") String contactName, Pageable pageable);

    @Query(value = "select u from ContactDelivery u where (:customerid is null or u.customer.customerId = :customerid) " +
            " and (:contactName is null or u.contactName like %:contactName%) " +
            " order by u.createedDate desc")
    Page<ContactDelivery> filterAllCustomer(@Param("customerid") Long customerid,
                                       @Param("contactName") String contactName, Pageable pageable);
}
