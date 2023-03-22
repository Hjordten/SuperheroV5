package com.example.superherov5.repositories;

import com.example.superherov5.dto.SuperheroAddToDatabaseDTO;
import com.example.superherov5.dto.SuperheroDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SuperheroRepo {

    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String uid;

    @Value("${spring.datasource.password}")
    private String pwd;

    public List<SuperheroDTO> getSuperheroes() {
        List<SuperheroDTO> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT * FROM superhero";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SuperheroDTO superhero = new SuperheroDTO();
                superhero.setHero_name(resultSet.getString("hero_name"));
                superhero.setReal_name(resultSet.getString("real_name"));
                superhero.setCreation_year(resultSet.getString("creation_year"));
                superhero.setSuperhero_id(resultSet.getString("superhero_id"));
                superheroes.add(superhero);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return superheroes;
    }

    public SuperheroDTO getSuperhero(String superhero_id) {
        SuperheroDTO superhero = new SuperheroDTO();
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT * FROM superhero WHERE superhero_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, superhero_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                superhero = new SuperheroDTO();
                superhero.setHero_name(resultSet.getString("hero_name"));
            }

            if (superhero != null) {
                SQL = "SELECT sp.superpower FROM superheropower shp JOIN superpower sp ON shp.superpower_id = sp.superpower_id WHERE shp.superhero_id = ?";
                preparedStatement = con.prepareStatement(SQL);
                preparedStatement.setString(1, superhero_id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    superhero.addPower(resultSet.getString("superpower"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return superhero;
    }





}

