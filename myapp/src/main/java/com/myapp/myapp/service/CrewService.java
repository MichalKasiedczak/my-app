package com.myapp.myapp.service;

import com.myapp.myapp.model.Crew;
import com.myapp.myapp.repository.CrewRepository;
import com.myapp.myapp.service.exception.AlreadyExists;
import com.myapp.myapp.service.exception.InvalidData;
import com.myapp.myapp.service.exception.NotFound;
import com.sun.xml.bind.v2.TODO;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrewService {
    @Autowired
    private CrewRepository crewRepository;

    @Transactional
    public List<String> getAllCrews() {
        return crewRepository.findAll().stream()
                .map(Crew::getCrewName)
                .collect(Collectors.toList());
    }

    @Transactional
    public String getCrewByName(String cewName) throws NotFound {
        Crew crew = crewRepository.findByCrewName(cewName)
                .orElseThrow(() -> createNotFoundByNameException(cewName) );
        return crew.getCrewName();
    }
    
    @Transactional
    public String createCrew(String crewName) throws InvalidData, AlreadyExists {
        validate(crewName);

        if(crewRepository.existsByCrewName(crewName)){
            throw new AlreadyExists("Role with same name already exists!");
        }

        Crew crew = Crew.builder().crewName(crewName).build();

        Crew saveCrew = crewRepository.save(crew);

        return saveCrew.getCrewName();
    }

    @Transactional
    public String deleteCrewByName(String crewName) throws NotFound {
        Crew crew = crewRepository.findByCrewName(crewName)
                .orElseThrow(() -> createNotFoundByNameException(crewName));

        crewRepository.delete(crew);

        return crew.getCrewName();
    }

    //TODO
    // nie wiem czy nie zrobić metod addUserToCrew oraz deleteUserFromCrew
    // albo zrobić relację UserCrewService

    private void validate(String crewName) throws InvalidData {
        if(crewName == null || crewName.isEmpty()) {
            throw new InvalidData("Crew must have an non-empty name!");
        }
    }

    private NotFound createNotFoundByNameException(String name) {
        return new NotFound("Crew with name " + name + " does not exist!");
    }

}
