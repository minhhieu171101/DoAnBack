package com.example.comic_store.controller;

import com.example.comic_store.constants.LoginConstants;
import com.example.comic_store.dto.*;
import com.example.comic_store.security.jwt.JwtUtils;
import com.example.comic_store.service.MailService;
import com.example.comic_store.service.UserService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtGenerator;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MailService mailService;

    private String tempGeneratedCode;

    private LocalDateTime codeGeneratedTime;

    @Value("${jwtCookieName}")
    private String tokenName;

    /**
     * Đăng nhập tài khoản hệ thống
     *
     * @param loginDTO Chứa thông tin username, password để đăng nhập
     * @return ResponseEntity<String> Chứa thông tin message
     * @vesion 1.0
     */
    @PostMapping("/login")
    public ResponseEntity<ServiceResult<AuthResponseDTO>> login(@RequestBody LoginDTO loginDTO) {
        ServiceResult<AuthResponseDTO> result = new ServiceResult<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                            loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            AuthResponseDTO authResponseDTO = new AuthResponseDTO(token);
            authResponseDTO.setTokenName(tokenName);
            result.setData(authResponseDTO);
            result.setMessage("Đăng nhập thành công!");
            result.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("err");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đăng nhập không thành công");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    /**
     * Đăng ký tài khoản hệ thống
     *
     * @param registerDTO Chứa thông tin cần thiết để đăng ký
     * @return ResponseEntity<String> Chứa thông tin message trả lại
     * @vesion 1.0
     */
    @PostMapping("/register")
    public ResponseEntity<ServiceResult<String>> register(@RequestBody RegisterDTO registerDTO) {
        ServiceResult<String> result = new ServiceResult<>();
        try {
            if (Math.abs(ChronoUnit.SECONDS.between(LocalDateTime.now(), this.codeGeneratedTime)) > 60) {
                result.setStatus(HttpStatus.BAD_REQUEST);
                result.setMessage("time is out of 60");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            result.setStatus(HttpStatus.OK);
            result.setMessage(userService.register(registerDTO, tempGeneratedCode));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("err");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Mã tài khoản sai");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gửi email xác thực
     *
     * @param mailDTO chứa thông tin về mail người đăng ký
     * @return CompletableFuture<ResponseEntity<ServiceResult<String>>> trả về tin nhắn
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @PostMapping("/send")
    @Async
    public CompletableFuture<ResponseEntity<ServiceResult<String>>> sendMailAuthorization(@RequestBody MailDTO mailDTO) {
        ServiceResult<String> result = new ServiceResult<>();
        try {
            this.tempGeneratedCode = userService.generateCode();
            this.codeGeneratedTime = LocalDateTime.now();
            result.setStatus(HttpStatus.OK);
            result.setMessage("Send email successfully!");

            mailDTO.setSubject(LoginConstants.SUBJECT_VALID);
            String messageBody = LoginConstants.INTRO_MAIL
                    + "\n"
                    + this.tempGeneratedCode
                    + "\n "
                    + LoginConstants.END_MAIL;
            mailDTO.setMessage(messageBody);
            mailService.sendMail(mailDTO);
            return CompletableFuture
                    .completedFuture(new ResponseEntity<>(result, HttpStatus.OK));

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Send email failed!");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Send email err!");
            return CompletableFuture
                    .completedFuture(new ResponseEntity<>(result, HttpStatus.BAD_REQUEST));
        }
    }

    /**
     * Lấy thông tin người dùng
     *
     * @param userDTO chứa tên người dùng
     * @return ServiceResult<UserDTO> chứa thông tin người dùng
     * @vesion 1.0
     */
    @PostMapping("/user")
    public ResponseEntity<ServiceResult<UserDTO>> getUserInfo(@RequestBody UserDTO userDTO) {
        ServiceResult<UserDTO> result = new ServiceResult<>();
        try {
            UserDTO userResponse = userService.getUserInfo(userDTO.getUsername());
            result.setData(userResponse);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Get info successfully!");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Send email failed!");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Get info err!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/page-user")
    public ResponseEntity<Page<UserDTO>> getPageUser(@RequestBody UserDTO userDTO) {
        Page<UserDTO> userDTOPage = userService.getUserPage(userDTO);
        return new ResponseEntity<>(userDTOPage, HttpStatus.OK);
    }


    @PostMapping("/update-user")
    public ResponseEntity<ServiceResult<String>> updateUserInfo(@RequestPart(value = "user") UserDTO userDTO,
                                                                @RequestPart(value = "file", required = false) MultipartFile file) {
        ServiceResult<String> result = userService.updateUserInfo(userDTO, file);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/verify_username")
    public ResponseEntity<ServiceResult<Boolean>>  checkExistUser(@RequestBody RegisterDTO registerDTO) {
        ServiceResult<Boolean> result = this.userService.checkExistUser(registerDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<ServiceResult<String>> deleteUser(@RequestBody UserDTO userDTO) {
        ServiceResult<String> result = userService.deleteUser(userDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

