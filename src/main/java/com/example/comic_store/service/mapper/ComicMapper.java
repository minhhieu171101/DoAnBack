package com.example.comic_store.service.mapper;

import com.example.comic_store.commons.Common;
import com.example.comic_store.dto.ComicAdminDTO;
import com.example.comic_store.dto.ComicDTO;
import com.example.comic_store.dto.ComicDetailDTO;
import com.example.comic_store.entity.Comic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class ComicMapper {

    public ComicAdminDTO toComicAdminDTO(Object[] object) {
        ComicAdminDTO comicAdminDTO = new ComicAdminDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            comicAdminDTO.setId((Long) object[0]);
            comicAdminDTO.setComicName((String) object[1]);
            comicAdminDTO.setAuthorName((String) object[2]);
            comicAdminDTO.setImgComic((String) object[3]);
            comicAdminDTO.setTypeName(Common.convertTypeName(((String) object[4])));
            comicAdminDTO.setReleaseDate((LocalDate) object[5]);
            comicAdminDTO.setPrice((Long) object[6]);
            comicAdminDTO.setSale((Long) object[7]);
            comicAdminDTO.setContents((String) object[8]);
            comicAdminDTO.setResidualQuantity((Long) object[9]);
        }
        return comicAdminDTO;
    }

    public Page<ComicAdminDTO> toComicAdminPageDTO(Page<Object[]> objects) {
        List<ComicAdminDTO> comicAdminDTOList = new ArrayList<>();
        for (Object[] object: objects) {
            comicAdminDTOList.add(toComicAdminDTO(object));
        }
        return new PageImpl<>(comicAdminDTOList, objects.getPageable(), objects.getTotalElements());
    }

    public Comic toComic(ComicAdminDTO comicAdminDTO) {
        Comic comic = new Comic();
        comic.setId(comicAdminDTO.getId());
        comic.setComicName(comicAdminDTO.getComicName());
        comic.setAuthorName(comicAdminDTO.getAuthorName());
        comic.setImgComic(comicAdminDTO.getImgComic());
        comic.setPrice(comicAdminDTO.getPrice());
        comic.setSale(comicAdminDTO.getSale());
        comic.setReleaseDate(comicAdminDTO.getReleaseDate());
        comic.setResidualQuantity(comicAdminDTO.getResidualQuantity());
        comic.setContents(comicAdminDTO.getContents());
        return comic;
    }

    public ComicDetailDTO toComicDetailDTO(Object[] object) {
        ComicDetailDTO comicDetailDTO = new ComicDetailDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            comicDetailDTO.setId((Long) object[0]);
            comicDetailDTO.setComicName((String) object[1]);
            comicDetailDTO.setAuthorName((String) object[2]);
            comicDetailDTO.setImgComic((String) object[3]);
            comicDetailDTO.setTypeName(Common.convertTypeName(((String) object[4])));
            comicDetailDTO.setPrice((Long) object[5]);
            comicDetailDTO.setSale((Long) object[6]);
            comicDetailDTO.setContents((String) object[7]);
            comicDetailDTO.setResidualQuantity((Long) object[8]);
            comicDetailDTO.setStatus((Integer) object[9]);
            comicDetailDTO.setTypeComicIds((String) object[10]);
            comicDetailDTO.setReleaseDate((LocalDate) object[11]);
        }
        return comicDetailDTO;
    }

    public ComicDTO toComicDTO(Object[] object) {
        ComicDTO comicAdminDTO = new ComicDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            comicAdminDTO.setId((Long) object[0]);
            comicAdminDTO.setComicName((String) object[1]);
            comicAdminDTO.setAuthorName((String) object[2]);
            comicAdminDTO.setContents((String) object[3]);
            comicAdminDTO.setPrice((Long) object[4]);
            comicAdminDTO.setSale((Long) object[5]);
            comicAdminDTO.setResidualQuantity((Long) object[6]);
            comicAdminDTO.setImgComic((String) object[7]);
        }
        return comicAdminDTO;
    }

    public Page<ComicDTO> toComicDTOPage(Page<Object[]> objectPage) {
        List<ComicDTO> comicAdminDTOList = new ArrayList<>();
        for (Object[] object: objectPage) {
            comicAdminDTOList.add(toComicDTO(object));
        }
        return new PageImpl<>(comicAdminDTOList, objectPage.getPageable(), objectPage.getTotalElements());
    }
}
