package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "select u from User u where u.warehouse.warehouseID =:warehouseId " +
            " and (:username is null or u.username like %:username%) " +
            " and (:roleId is null or u.role.roleID = :roleId) and u.userId <> :userId" +
            " order by u.createdDate desc")
    Page<User> filterInOneWarehouse(@Param("username") String username, @Param("roleId") Long roleId,
                                    @Param("warehouseId") Long warehouseId,
                                    @Param("userId") Long userId, Pageable pageable);

    @Query(value = "select u from User u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " and (:username is null or u.username like %:username%) " +
            " and (:roleId is null or u.role.roleID = :roleId) and u.userId <> :userId" +
            " order by u.createdDate desc")
    Page<User> filterAllWarehouses(@Param("username") String username, @Param("roleId") Long roleId,
                                   @Param("warehouseId") Long warehouseId,
                                   @Param("userId") Long userId, Pageable pageable);

}
