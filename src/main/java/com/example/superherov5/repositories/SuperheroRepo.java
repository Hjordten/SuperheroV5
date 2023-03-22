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

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT * FROM city";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cities.add(resultSet.getString("city"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    public List<String> getPowers() {
        List<String> powers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            String SQL = "SELECT * FROM superpower";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                powers.add(resultSet.getString("superpower"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return powers;
    }

    public void addSuperheroToDatabase(SuperheroAddToDatabaseDTO form) {
        try (Connection con = DriverManager.getConnection(db_url, uid, pwd)) {
            int cityId = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();

            // Find city_id
            String SQL1 = "SELECT city_id FROM city WHERE city = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, form.getCity());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cityId = rs.getInt("city_id");
            }

            // Insert row in superhero table
            String SQL2 = "INSERT INTO superhero (hero_name, real_name, creation_year, city_id) VALUES(?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, form.getHero_name());
            pstmt.setString(2, form.getReal_name());
            pstmt.setString(3, form.getCreation_year());
            pstmt.setInt(4, cityId);
            int rows = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                heroId = rs.getInt(1);
            }

            // Find power_ids
            String SQL3 = "SELECT superpower_id FROM superpower WHERE superpower = ?;";
            pstmt = con.prepareStatement(SQL3);
            for (String power : form.getPowers()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("superpower_id"));
                }
            }

            // Insert entries in superhero_powers join table
            String SQL4 = "INSERT INTO superheropower (superhero_id, superpower_id) VALUES (?, ?);";
            pstmt = con.prepareStatement(SQL4);
            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId);
                pstmt.setInt(2, powerIDs.get(i));
                rows = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
