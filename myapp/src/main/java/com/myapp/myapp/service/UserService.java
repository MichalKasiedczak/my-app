package com.myapp.myapp.service;

import com.myapp.myapp.model.User;
import com.myapp.myapp.repository.UserRepository;
import com.myapp.myapp.service.dto.CreateUserDto;
import com.myapp.myapp.service.dto.UpdateUserDto;
import com.myapp.myapp.service.dto.UserDto;
import com.myapp.myapp.service.exception.AlreadyExists;
import com.myapp.myapp.service.exception.InvalidData;
import com.myapp.myapp.service.exception.NotFound;
import com.myapp.myapp.service.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoMapper userDtoMapper;

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(user -> userDtoMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto getUserById(int id) throws NotFound {
        User user = userRepository.findById(id)
                .orElseThrow(() -> createNotFoundByIdException(id));

        return userDtoMapper.toDto(user);
    }

    @Transactional
    public UserDto getUserByLogin(String login) throws NotFound {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> createNotFoundByLogin(login));

        return userDtoMapper.toDto(user);
    }

    @Transactional
    public UserDto createUser(CreateUserDto createDto) throws InvalidData, AlreadyExists {
        validate(createDto);

        if (userRepository.existsByLogin(createDto.getLogin())) {
            throw new AlreadyExists("User with same login already exists!");
        }

        User user = userDtoMapper.toModel(createDto);

        User savedUser = userRepository.save(user);

        return userDtoMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto updateUser(UpdateUserDto updateDto, int id) throws NotFound, InvalidData {
        validate(updateDto);

        User user = userRepository.findById(id)
                .orElseThrow(() -> createNotFoundByIdException(id));


        user.setPassword(updateDto.getPassword());

        User savedUser = userRepository.save(user);

        return userDtoMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto deleteUserById(int id) throws NotFound {
        User user = userRepository.findById(id)
                .orElseThrow(() -> createNotFoundByIdException(id));

        userRepository.delete(user);

        return userDtoMapper.toDto(user);
    }



    private void validate(CreateUserDto createDto) throws InvalidData {
        if (createDto.getLogin() == null || createDto.getLogin().isEmpty()) {
            throw new InvalidData("User must have an non-empty login!");
        }

        if (createDto.getPassword() == null || createDto.getPassword().isEmpty()) {
            throw new InvalidData("User must have an non-empty password!");
        }
    }

    private void validate(UpdateUserDto updateDto) throws InvalidData {
        if (updateDto.getPassword() == null || updateDto.getPassword().isEmpty()) {
            throw new InvalidData("User must have an non-empty password!");
        }
    }

    private NotFound createNotFoundByIdException(int id) {
        return new NotFound("User with id " + id + " does not exist!");
    }

    private NotFound createNotFoundByLogin(String login) {
        return new NotFound("User with login " + login + " does not exist!");
    }

}
