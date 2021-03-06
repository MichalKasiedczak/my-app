package com.myapp.myapp.repository;

import com.myapp.myapp.model.Crew;
import com.myapp.myapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByRoleName(String roleName);
    Optional<Role> findByRoleName(String roleName);
}
