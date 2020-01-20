package com.myapp.myapp.repository;

import com.myapp.myapp.model.Crew;
import com.myapp.myapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Integer> {
    boolean existsByCrewName(String crewName);
    Optional<Crew> findByCrewName(String crewName);
}
