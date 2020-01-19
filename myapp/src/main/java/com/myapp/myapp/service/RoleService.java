package com.myapp.myapp.service;

import com.myapp.myapp.model.Role;
import com.myapp.myapp.repository.RoleRepository;
import com.myapp.myapp.service.dto.CreateUserDto;
import com.myapp.myapp.service.exception.AlreadyExists;
import com.myapp.myapp.service.exception.InvalidData;
import com.myapp.myapp.service.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public List<String> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    @Transactional
    public String getRoleByName(String roleName) throws NotFound {
        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> createNotFoundByNameException(roleName));

        return role.getName();
    }

    @Transactional
    public String createRole(String roleName) throws InvalidData, AlreadyExists {
        validate(roleName);

        if (roleRepository.existsById(roleName)) {
            throw new AlreadyExists("Rolee with same name already exists!");
        }

        Role role = Role.builder().name(roleName).build();

        Role savedRole = roleRepository.save(role);

        return savedRole.getName();
    }

    @Transactional
    public String deleteRoleByName(String roleName) throws NotFound {
        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> createNotFoundByNameException(roleName));

        roleRepository.delete(role);

        return role.getName();
    }

    private void validate(CreateUserDto createDto) throws InvalidData {
        if (createDto.getLogin() == null || createDto.getLogin().isEmpty()) {
            throw new InvalidData("User must have an non-empty login!");
        }

        if (createDto.getPassword() == null || createDto.getPassword().isEmpty()) {
            throw new InvalidData("User must have an non-empty password!");
        }
    }

    private void validate(String roleName) throws InvalidData {
        if (roleName == null || roleName.isEmpty()) {
            throw new InvalidData("Role must have an non-empty name!");
        }
    }

    private NotFound createNotFoundByNameException(String name) {
        return new NotFound("Role with name " + name + " does not exist!");
    }
}


