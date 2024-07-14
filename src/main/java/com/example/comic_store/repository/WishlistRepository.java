package com.example.comic_store.repository;

import com.example.comic_store.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query(
            value = "SELECT" +
                    " w.id," +
                    " c.id," +
                    " ue.id," +
                    " c.comicName," +
                    " c.authorName," +
                    " GROUP_CONCAT(tc.typeName, ', ') AS typeName," +
                    " c.contents," +
                    " c.imgComic," +
                    " ue.username " +
                    "FROM Wishlist w " +
                    "INNER JOIN Comic c ON w.comicId = c.id " +
                    "INNER JOIN UserEntity ue ON w.createdBy = ue.id " +
                    "INNER JOIN TypeComic tc " +
                    "ON CAST(FIND_IN_SET(CONCAT('s', tc.id, 'e') , c.typeComicIds) AS int) > CAST(0 AS int) " +
                    "WHERE ue.username = :username " +
                    "GROUP BY " +
                    " w.id," +
                    " c.id," +
                    " c.typeComicIds " +
                    "ORDER BY w.createdAt",
            countQuery = "SELECT count(w) FROM Wishlist w"
    )
    Page<Object[]> getAllWishList(Pageable pageable, @Param("username") String username);

    Wishlist findByComicId(Long id);
}
