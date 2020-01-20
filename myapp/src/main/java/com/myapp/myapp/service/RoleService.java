package com.myapp.myapp.service;

import com.myapp.myapp.model.Role;
import com.myapp.myapp.repository.RoleRepository;
import com.myapp.myapp.service.dto.UserCreateDto;
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
                .map(Role::getRoleName)
                .collect(Collectors.toList());
    }

    @Transactional
    public String getRoleByName(String roleName) throws NotFound {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> createNotFoundByNameException(roleName));

        return role.getRoleName();
    }

    @Transactional
    public String createRole(String roleName) throws InvalidData, AlreadyExists {
        validate(roleName);

        if (roleRepository.existsByRoleName(roleName)) {
            throw new AlreadyExists("Role with same name already exists!");
        }

        Role role = Role.builder().roleName(roleName).build();

        Role savedRole = roleRepository.save(role);

        return savedRole.getRoleName();
    }

    @Transactional
    public String deleteRoleByName(String roleName) throws NotFound {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> createNotFoundByNameException(roleName));

        roleRepository.delete(role);

        return role.getRoleName();
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


