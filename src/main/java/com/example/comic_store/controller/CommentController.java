package com.example.comic_store.controller;

import com.example.comic_store.dto.CommentDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/page-comment")
    public ResponseEntity<Page<CommentDTO>> getPageComment(@RequestBody CommentDTO commentDTO) {
        Page<CommentDTO> commentDTOS = commentService.getPageComment(commentDTO);
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

//    @PostMapping("/comment-comic")
//    public ResponseEntity<Page<CommentDTO>> getPageCommentComic(@RequestBody CommentDTO commentDTO) {
//        Page<CommentDTO> commentDTOS = commentService.getPageCommentComic(commentDTO);
//        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
//    }

    @PostMapping("/update-comment")
    public ResponseEntity<ServiceResult<String>> updateComment(@RequestBody CommentDTO commentDTO) {
        ServiceResult<String> result = commentService.updateComment(commentDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/delete-comment")
    public ResponseEntity<ServiceResult<String>> deleteComment(@RequestBody CommentDTO commentDTO) {
        ServiceResult<String> result = commentService.deleteComment(commentDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
