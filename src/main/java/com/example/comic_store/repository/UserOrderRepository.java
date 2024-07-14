package com.example.comic_store.repository;

import com.example.comic_store.entity.UserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

    @Query(
            value = "SELECT " +
                    " ue.id," +
                    " uo.id, " +
                    " ue.fullName," +
                    " ue.address," +
                    " ue.phone," +
                    " uo.totalPrice," +
                    " uo.orderDate," +
                    " uo.status " +
                    "FROM UserOrder uo " +
                    "INNER JOIN UserEntity ue ON uo.userId = ue.id " +
                    "WHERE 1 = 1 " +
                    "AND (:searchKey IS NULL OR LOWER(ue.fullName) LIKE CONCAT('%',LOWER(:searchKey), '%') " +
                    "OR LOWER(ue.address) LIKE CONCAT('%',LOWER(:searchKey), '%')" +
                    "OR LOWER(ue.phone) LIKE CONCAT('%',LOWER(:searchKey), '%')) " +
                    "ORDER BY uo.createdAt desc",
            countQuery = "SELECT COUNT(*) " +
                    "FROM UserOrder uo " +
                    "INNER JOIN UserEntity ue ON uo.userId = ue.id " +
                    "WHERE 1 = 1 " +
                    "AND (:searchKey IS NULL OR LOWER(ue.fullName) LIKE CONCAT('%',LOWER(:searchKey), '%') " +
                    "OR LOWER(ue.address) LIKE CONCAT('%',LOWER(:searchKey), '%')" +
                    "OR LOWER(ue.phone) LIKE CONCAT('%',LOWER(:searchKey), '%')) " +
                    "ORDER BY uo.createdAt"
    )
    Page<Object[]> getAllPurchaseOrder(Pageable pageable, @Param("searchKey") String searchKey);


    @Query(
            value = "SELECT " +
                    " ue.id," +
                    " uo.id, " +
                    " ue.fullName," +
                    " ue.address," +
                    " ue.phone," +
                    " uo.totalPrice," +
                    " uo.orderDate," +
                    " uo.status " +
                    "FROM UserOrder uo " +
                    "INNER JOIN UserEntity ue ON uo.userId = ue.id " +
                    "WHERE uo.userId = :userId " +
                    "AND (:searchKey IS NULL OR CAST(uo.id as CHARACTER ) LIKE CONCAT('%',LOWER(:searchKey), '%')) " +
                    "ORDER BY uo.createdAt",
            countQuery = "SELECT COUNT(uo) FROM UserOrder uo"
    )
    Page<Object[]> getAllPurchaseOrderUser(Pageable pageable,
                                           @Param("userId") Long userId,
                                           @Param("searchKey") String searchKey );


}
