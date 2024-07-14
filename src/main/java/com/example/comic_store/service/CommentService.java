package com.example.comic_store.service;

import com.example.comic_store.dto.CommentDTO;
import com.example.comic_store.dto.ServiceResult;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentDTO> getPageComment(CommentDTO commentDTO);
    Page<CommentDTO> getPageCommentComic(CommentDTO commentDTO);
    ServiceResult<String> updateComment(CommentDTO commentDTO);
    ServiceResult<String> deleteComment(CommentDTO commentDTO);
}
