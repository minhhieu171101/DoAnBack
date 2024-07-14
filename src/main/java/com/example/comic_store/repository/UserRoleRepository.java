package com.example.comic_store.repository;

import com.example.comic_store.entity.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "SELECT ur.roleId FROM UserRole ur WHERE ur.userId = :userId")
    List<Long> findAllUserRoleIdById(@Param("userId") Long userId);
}
