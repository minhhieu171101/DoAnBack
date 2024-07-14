package com.example.comic_store.repository;

import com.example.comic_store.entity.TypeComic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeComicRepository extends JpaRepository<TypeComic, Long> {
    TypeComic findTypeComicByTypeName(String typeName);

    @Query(
            value = "SELECT tc " +
                    "FROM TypeComic tc " +
                    "WHERE LOWER(tc.typeName) IN :typeNameList"
    )
    List<TypeComic> findAllByTypeNameIn(@Param("typeNameList") List<String> typeNameList);
}
