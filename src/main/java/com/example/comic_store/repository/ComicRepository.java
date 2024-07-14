package com.example.comic_store.repository;

import com.example.comic_store.entity.Comic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComicRepository extends JpaRepository<Comic, Long> {

    @Query(value = "SELECT c " +
                    "FROM Comic c " +
                    "ORDER BY c.createdAt DESC ",
    countQuery =    "SELECT COUNT(c) FROM Comic c")
    Page<Comic> getComicLandingPage(Pageable pageable);

    @Query(
            value = "SELECT " +
                    " c.id, " +
                    " c.comicName," +
                    " c.authorName," +
                    " c.imgComic," +
                    " GROUP_CONCAT(tc.typeName, ', ') AS typeName," +
                    " c.price, " +
                    " c.sale," +
                    " c.contents," +
                    " c.residualQuantity," +
                    " c.status," +
                    " c.typeComicIds," +
                    " c.releaseDate " +
                    "FROM Comic c " +
                    "LEFT JOIN TypeComic tc " +
                    "ON CAST(FIND_IN_SET(CONCAT('s', tc.id, 'e') , c.typeComicIds) AS int) > CAST(0 AS int) " +
                    "WHERE c.id = :id " +
                    "GROUP BY " +
                    " c.id," +
                    " c.typeComicIds "
    )
    Object[] getComicBy(@Param("id") Long id);

    @Query(
            value = "SELECT c " +
                    "FROM Comic c " +
                    "WHERE c.typeComicIds LIKE CONCAT('%', :typeComicId, '%') " +
                    "ORDER BY c.createdAt",
            countQuery = "SELECT COUNT(c) " +
                         "FROM Comic c " +
                         "WHERE c.typeComicIds LIKE CONCAT('%', :typeComicId, '%') "
    )
    Page<Comic> getAllByTypeComicId(Pageable pageable, @Param("typeComicId") String typeComicId);

    @Query(
            value = "SELECT " +
                    " c.id, " +
                    " c.comicName," +
                    " c.authorName," +
                    " c.imgComic," +
                    " GROUP_CONCAT(tc.typeName, ', ') AS typeName," +
                    " c.releaseDate," +
                    " c.price, " +
                    " c.sale," +
                    " c.contents, " +
                    " c.residualQuantity " +
                    "FROM Comic c " +
                    "LEFT JOIN TypeComic tc " +
                    "ON CAST(FIND_IN_SET(CONCAT('s', tc.id, 'e') , c.typeComicIds) AS int) > CAST(0 AS int) " +
                    "WHERE 1 = 1 " +
                    "AND (:searchKey IS NULL OR LOWER(c.comicName) LIKE CONCAT('%',LOWER(:searchKey), '%') " +
                    "OR LOWER(c.authorName) LIKE CONCAT('%',LOWER(:searchKey), '%')" +
                    "OR LOWER(tc.typeName) LIKE CONCAT('%',LOWER(:searchKey), '%')) " +
                    "GROUP BY " +
                    " c.id," +
                    " c.typeComicIds " +
                    " ORDER BY c.createdAt DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM Comic c " +
                    "LEFT JOIN TypeComic tc " +
                    "ON CAST(FIND_IN_SET(CONCAT('s', tc.id, 'e') , c.typeComicIds) AS int) > CAST(0 AS int) " +
                    "WHERE 1 = 1 " +
                    "AND (:searchKey IS NULL OR LOWER(c.comicName) LIKE CONCAT('%',LOWER(:searchKey), '%') " +
                    "OR LOWER(c.authorName) LIKE CONCAT('%',LOWER(:searchKey), '%')" +
                    "OR LOWER(tc.typeName) LIKE CONCAT('%',LOWER(:searchKey), '%')) " +
                    "GROUP BY " +
                    " c.id," +
                    " c.typeComicIds "
    )
    Page<Object[]> getAllComic(Pageable pageable,@Param("searchKey") String searchKey);


    @Query(
            value = "SELECT " +
                    " c.id, " +
                    " c.comicName," +
                    " c.authorName," +
                    " c.contents, " +
                    " c.price, " +
                    " c.sale," +
                    " c.residualQuantity," +
                    " c.imgComic " +
                    "FROM Comic c " +
                    "WHERE 1 = 1 " +
                    "AND (:searchKey IS NULL OR LOWER(c.comicName) LIKE CONCAT('%',LOWER(:searchKey), '%')) " +
                    "GROUP BY " +
                    " c.id " +
                    " ORDER BY c.createdAt DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM Comic c " +
                    "LEFT JOIN TypeComic tc " +
                    "WHERE 1 = 1 " +
                    "AND (:searchKey IS NULL OR LOWER(c.comicName) LIKE CONCAT('%',LOWER(:searchKey), '%')) " +
                    "GROUP BY " +
                    " c.id"
    )
    Page<Object[]> searchComic(Pageable pageable, @Param("searchKey") String searchKey);
}
