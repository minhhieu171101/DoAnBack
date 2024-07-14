package com.example.comic_store.dto;

import java.io.Serializable;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ServiceResult<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private T data;
}
