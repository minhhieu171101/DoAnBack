package com.example.comic_store.repository;

import com.example.comic_store.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query(
            value = "SELECT " +
                    " ue.id, " +
                    " ue.username," +
                    " ue.fullName," +
                    " ue.birthday," +
                    " ue.address," +
                    " ue.phone," +
                    " ue.email," +
                    " ue.imgUser," +
                    " ue.gender " +
                    "FROM UserEntity ue " +
                    "WHERE 1 = 1 " +
                    "AND (:searchKey IS NULL OR LOWER(ue.username) LIKE CONCAT('%', LOWER(:searchKey), '%') " +
                    "OR LOWER(ue.fullName) LIKE CONCAT('%', LOWER(:searchKey), '%') " +
                    "OR LOWER(ue.email) LIKE CONCAT('%', LOWER(:searchKey), '%') " +
                    "OR LOWER(ue.phone) LIKE CONCAT('%', LOWER(:searchKey), '%') " +
                    "OR LOWER(ue.address) LIKE CONCAT('%', LOWER(:searchKey), '%')) " +
                    "ORDER BY ue.createdAt DESC"
    )
    Page<Object[]> getPageUser(Pageable pageable, @Param("searchKey") String searchKey);
}
