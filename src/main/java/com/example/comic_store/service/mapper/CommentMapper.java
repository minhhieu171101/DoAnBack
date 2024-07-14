package com.example.comic_store.service.mapper;

import com.example.comic_store.dto.CommentDTO;
import com.example.comic_store.entity.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    CommentDTO toCommentDTO(Object[] object) {
        CommentDTO commentDTO = new CommentDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            commentDTO.setId((Long) object[0]);
            commentDTO.setUserId((Long) object[1]);
            commentDTO.setComicId((Long) object[2]);
            commentDTO.setFullName((String) object[3]);
            commentDTO.setComicName((String) object[4]);
            commentDTO.setContent((String) object[5]);
            commentDTO.setDatePost((LocalDateTime) object[6]);
            commentDTO.setImgComment((String) object[7]);
        }
        return commentDTO;
    }

    public Page<CommentDTO> toCommentDTOPAge(Page<Object[]> objects) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Object[] object: objects) {
            commentDTOS.add(toCommentDTO(object));
        }
        return new PageImpl<>(commentDTOS, objects.getPageable(), objects.getTotalElements());
    }
}
