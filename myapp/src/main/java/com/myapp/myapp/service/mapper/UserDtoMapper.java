package com.myapp.myapp.service.mapper;

import com.myapp.myapp.model.User;
import com.myapp.myapp.service.dto.CreateUserDto;
import com.myapp.myapp.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.UUID;

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
    public User toModel(CreateUserDto createDto) {
        return User.builder()
                .login(createDto.getLogin())
                .password(createDto.getPassword())
                .createdAt(OffsetDateTime.now())
                .build();
    }

}
