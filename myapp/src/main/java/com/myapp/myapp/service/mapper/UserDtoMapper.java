package com.myapp.myapp.service.mapper;

import com.myapp.myapp.model.User;
import com.myapp.myapp.service.dto.UserCreateDto;
import com.myapp.myapp.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class UserDtoMapper {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

    }
    public User toModel(UserCreateDto createDto) {
        return User.builder()
                .login(createDto.getLogin())
                .password(createDto.getPassword())
                .createdAt(OffsetDateTime.now())
                .build();
    }

}
