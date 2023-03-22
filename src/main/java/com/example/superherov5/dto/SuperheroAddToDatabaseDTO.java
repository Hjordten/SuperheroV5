package com.example.superherov5.dto;

import java.util.ArrayList;
import java.util.List;

public class SuperheroAddToDatabaseDTO {
    private String hero_name;
    private String real_name;
    private String creation_year;
    private String city;
    private List<String> powers = new ArrayList<>();
    private List<String> cities = new ArrayList<>();

    public SuperheroAddToDatabaseDTO() {
    }

    public void setHero_name(String hero_name) {
        this.hero_name = hero_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setCreation_year(String creation_year) {
        this.creation_year = creation_year;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPowers(List<String> powers) {
        this.powers = powers;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public String getHero_name() {
        return hero_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getCreation_year() {
        return creation_year;
    }

    public String getCity() {
        return city;
    }

    public List<String> getPowers() {
        return powers;
    }

    public List<String> getCities() {
        return cities;
    }
}
