package com.example.comic_store.dto;

import java.util.Date;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private Date birthday;
    private String address;
    private String phone;
    private String email;
    private String imgUser;
    private Integer gender;
    private int page;
    private int pageSize;
    private String searchKey;
    private String password;
    private String rePassword;
}
