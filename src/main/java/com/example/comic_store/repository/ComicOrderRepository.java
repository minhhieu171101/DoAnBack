package com.example.comic_store.repository;

import com.example.comic_store.entity.ComicOrder;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicOrderRepository extends JpaRepository<ComicOrder, Long> {
    @Query(
            value = "SELECT " +
                    "   u.id," +
                    "   c.id," +
                    "   co.id," +
                    "   c.comicName," +
                    "   c.imgComic," +
                    "   co.quantity," +
                    "   co.totalPrice," +
                    " GROUP_CONCAT(tc.typeName, ', ') AS typeName " +
                    "FROM UserEntity u " +
                    "LEFT JOIN ComicOrder co ON u.id = co.userId " +
                    "INNER JOIN Comic c ON co.comicId = c.id " +
                    "INNER JOIN TypeComic tc " +
                    "ON CAST(FIND_IN_SET(CONCAT('s', tc.id, 'e') , c.typeComicIds) AS int) > CAST(0 AS int) " +
                    "WHERE u.username = :username " +
                    "AND co.status = 0 " +
                    "GROUP BY " +
                    "   u.id," +
                    "   co.id," +
                    "   c.id," +
                    "   c.typeComicIds " +
                    "ORDER BY co.updatedAt DESC"
    )
    List<Object[]> getAllByUsername(@Param("username") String username);

    @Query(
            value = "SELECT " +
                    "tc.typeName, " +
                    "SUM(co.quantity) AS totalSold, " +
                    "SUM(co.totalPrice) AS totalIncome " +
                    "FROM ComicOrder co " +
                    "INNER JOIN Comic c ON co.comicId = c.id " +
                    "INNER JOIN TypeComic tc " +
                    "ON CAST(FIND_IN_SET(CONCAT('s', tc.id, 'e') , c.typeComicIds) AS int) > CAST(0 AS int) " +
                    "LEFT JOIN UserOrder uo ON co.userOrderId = uo.id " +
                    "WHERE co.status = 1 and uo.status = 2 " +
                    "AND co.updatedAt >= :startDate AND co.updatedAt < :endDate " +
                    "GROUP BY " +
                    " tc.id " +
                    "ORDER BY totalIncome DESC"
    )
    List<Object[]> getStatisticMonth(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    ComicOrder findByComicIdAndStatusAndUserId (Long id, Long status, Long userId);
}
