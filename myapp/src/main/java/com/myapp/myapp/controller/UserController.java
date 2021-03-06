package com.myapp.myapp.controller;

import com.myapp.myapp.service.UserService;
import com.myapp.myapp.service.dto.UserCreateDto;
import com.myapp.myapp.service.dto.UserUpdateDto;
import com.myapp.myapp.service.dto.UserDto;
import com.myapp.myapp.service.exception.AlreadyExists;
import com.myapp.myapp.service.exception.InvalidData;
import com.myapp.myapp.service.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/useres")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    private List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    private UserDto getUserById(@PathVariable int id) throws NotFound {
        return userService.getUserById(id);
    }

    @PostMapping
    private UserDto createUser(@Valid @RequestBody UserCreateDto createDto) throws InvalidData, AlreadyExists {
        return userService.createUser(createDto);
    }

    @PutMapping("/{id}")
    private UserDto updateUser(@Valid @RequestBody UserUpdateDto updateDto, @PathVariable int id) throws NotFound, InvalidData {
        return userService.updateUser(updateDto, id);
    }

    @DeleteMapping("/{id}")
    private UserDto deleteUserById(@PathVariable int id) throws NotFound {
        return userService.deleteUserById(id);
    }



}
