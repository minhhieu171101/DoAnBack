package com.example.comic_store.service.impl;

import com.example.comic_store.dto.CommentDTO;
import com.example.comic_store.dto.RegisterDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.UserDTO;
import com.example.comic_store.entity.Comment;
import com.example.comic_store.entity.Role;
import com.example.comic_store.entity.UserEntity;
import com.example.comic_store.entity.UserRole;
import com.example.comic_store.repository.RoleRepository;
import com.example.comic_store.repository.UserRepository;
import com.example.comic_store.repository.UserRoleRepository;
import com.example.comic_store.service.UserService;
import com.example.comic_store.service.mapper.UserMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserMapper userMapper;

    @Value("${AVATAR_FOLDER_PATH}")
    private String folderImgPath;

    @Value("${AVATAR_TARGET_FOLDER_PATH}")
    private String folderTargetPath;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public String register(RegisterDTO registerDTO, String code) {
        if (!registerDTO.getCode().equals(code)) {
            return "code is wrong!";
        }
        UserEntity user = modelMapper.map(registerDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        UserEntity userSave = userRepository.save(user);

        Role role = roleRepository.findByRoleName("ROLE_USER");

        UserRole userRole = new UserRole();
        userRole.setUserId(userSave.getId());
        userRole.setRoleId(role.getId());

        userRoleRepository.save(userRole);
        return "register successfully!";
    }

    /**
     * sinh ra code cho việc xác thực eaml
     *
     * @return String là mã code sau khi random 6 chữ số
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @Override
    public String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int num = 0; num < 6; num ++) {
            code.append(random.nextInt(0, 10));
        }
        return code.toString();
    }

    @Override
    public UserDTO getUserInfo(String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        user.setPassword(null);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public Page<UserDTO> getUserPage(UserDTO userDTO) {
        Pageable pageable = PageRequest.of(userDTO.getPage(), userDTO.getPageSize());
        Page<Object[]> userEntityPage = userRepository.getPageUser(pageable, userDTO.getSearchKey());
        return userMapper.toUserDTOPage(userEntityPage);
    }

    @Override
    public ServiceResult<String> updateUserInfo(UserDTO userDTO, MultipartFile file) {
        UserEntity user = userRepository.findById(userDTO.getId()).orElse(null);
        ServiceResult<String> result = new ServiceResult<>();
        if (user != null) {
            user.setUsername(userDTO.getUsername());
            user.setAddress(userDTO.getAddress());
            user.setBirthday(userDTO.getBirthday());
            user.setEmail(userDTO.getEmail());
            user.setGender(userDTO.getGender());
            user.setFullName(userDTO.getFullName());
            user.setPhone(userDTO.getPhone());
            user.setUpdatedAt(LocalDateTime.now());
            if (file != null) {
                user.setImgUser(file.getOriginalFilename());
                Path path = Path.of(folderImgPath + file.getOriginalFilename());
                Path pathTarget = Path.of(folderTargetPath + file.getOriginalFilename());
                try {
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(file.getInputStream(), pathTarget, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (userDTO.getPassword() == null && userDTO.getRePassword() == null) {
                userRepository.save(user);
            } else if (userDTO.getPassword() != null
                    && !userDTO.getPassword().trim().equals("")
                    && userDTO.getRePassword() != null
                    && userDTO.getPassword().equals(userDTO.getRePassword())) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(user);
            } else {
                result.setStatus(HttpStatus.BAD_REQUEST);
                result.setMessage("Cập nhật thông tin người dùng thất bại!");
                return result;
            }
        }
        result.setStatus(HttpStatus.OK);
        result.setMessage("Cập nhật thông tin người dùng thành công!");
        return result;
    }

    @Override
    public ServiceResult<Boolean> checkExistUser(RegisterDTO registerDTO) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<>();
        if (this.userRepository.existsByUsername(registerDTO.getUsername())) {
            serviceResult.setData(true);
            serviceResult.setMessage("Tài khoản đã tồn tại!");
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            return serviceResult;
        }
        serviceResult.setData(false);
        serviceResult.setMessage("Tài khoản chưa tồn tại!");
        serviceResult.setStatus(HttpStatus.OK);
        return serviceResult;
    }

    @Override
    public ServiceResult<String> deleteUser(UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findById(userDTO.getId());
        ServiceResult<String> result = new ServiceResult<>();
        if (user.isPresent()) {
            userRepository.delete(user.orElse(null));
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xóa tài khoản thành công1");
            return result;
        }
        result.setStatus(HttpStatus.BAD_REQUEST);
        result.setMessage("Xóa tài khoản không thành công1");
        return result;
    }
}
