package com.example.comic_store.service;

import com.example.comic_store.dto.CommentDTO;
import com.example.comic_store.dto.RegisterDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    String register(RegisterDTO registerDTO, String code);
    String generateCode();
    UserDTO getUserInfo(String username);
    Page<UserDTO> getUserPage(UserDTO userDTO);
    ServiceResult<String> updateUserInfo(UserDTO userDTO, MultipartFile file);
    ServiceResult<Boolean> checkExistUser(RegisterDTO registerDTO);
    ServiceResult<String> deleteUser(UserDTO userDTO);
}
