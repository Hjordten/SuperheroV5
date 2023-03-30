package com.example.superherov5.repositories;

import com.example.superherov5.dto.SuperheroAddToDatabaseDTO;
import com.example.superherov5.dto.SuperheroDTO;

import java.util.List;


public interface ISuperheroRepository {


    List<SuperheroDTO> getSuperheroes();

    SuperheroDTO getSuperhero(String superheroId);

    List<String> getCities();

    List<String> getPowers();

    void addSuperheroToDatabase(SuperheroAddToDatabaseDTO superheroDto);

    void deleteSuperhero(int superheroId);
}
