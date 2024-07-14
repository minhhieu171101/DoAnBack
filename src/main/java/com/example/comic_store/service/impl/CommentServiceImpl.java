package com.example.comic_store.service.impl;

import com.example.comic_store.dto.CommentDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.entity.Comment;
import com.example.comic_store.repository.CommentRepository;
import com.example.comic_store.service.CommentService;
import com.example.comic_store.service.mapper.CommentMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<CommentDTO> getPageComment(CommentDTO commentDTO) {
        Pageable pageable = PageRequest.of(commentDTO.getPage(), commentDTO.getPageSize());
        Page<Object[]> commentPage = commentRepository.getCommentPage(pageable, commentDTO.getSearchKey());
        return commentMapper.toCommentDTOPAge(commentPage);
    }

    @Override
    public Page<CommentDTO> getPageCommentComic(CommentDTO commentDTO) {
        Pageable pageable = PageRequest.of(commentDTO.getPage(), commentDTO.getPageSize());
        Page<Object[]> commentPage = commentRepository.getCommentPageByComicId(pageable, commentDTO.getComicId());
        return commentMapper.toCommentDTOPAge(commentPage);
    }

    @Override
    public ServiceResult<String> updateComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setDatePost(LocalDateTime.now());
        commentRepository.save(comment);
        ServiceResult<String> result = new ServiceResult<>();
        result.setStatus(HttpStatus.OK);
        result.setMessage("Viết đánh giá thành công!");
        return result;
    }

    @Override
    public ServiceResult<String> deleteComment(CommentDTO commentDTO) {
        Optional<Comment> comment = commentRepository.findById(commentDTO.getId());
        ServiceResult<String> result = new ServiceResult<>();
        if (comment.isPresent()) {
            commentRepository.delete(comment.orElse(null));
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xóa đánh giá thành công1");
            return result;
        }
        result.setStatus(HttpStatus.BAD_REQUEST);
        result.setMessage("Xóa đánh giá không thành công1");
        return result;
    }
}
