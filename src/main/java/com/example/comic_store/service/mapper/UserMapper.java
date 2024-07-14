package com.example.comic_store.service.mapper;

import com.example.comic_store.dto.UserDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(Object[] object) {
        UserDTO userDTO = new UserDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            userDTO.setId((Long) object[0]);
            userDTO.setUsername((String) object[1]);
            userDTO.setFullName((String) object[2]);
            userDTO.setBirthday((Date) object[3]);
            userDTO.setAddress((String) object[4]);
            userDTO.setPhone((String) object[5]);
            userDTO.setEmail((String) object[6]);
            userDTO.setImgUser((String) object[7]);
            userDTO.setGender((Integer) object[8]);
        }
        return userDTO;
    }

    public Page<UserDTO> toUserDTOPage(Page<Object[]> objects) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (Object[] object : objects) {
            userDTOS.add(toUserDTO(object));
        }
        return new PageImpl<>(userDTOS, objects.getPageable(), objects.getTotalElements());
    }
}
