package com.example.superherov5.dto;

import java.util.ArrayList;
import java.util.List;

public class SuperheroDTO {
    private int superhero_id;
    private String hero_name;
    private String real_name;
    private String creation_year;
    private List<String> powers = new ArrayList<>();

    public SuperheroDTO() {
    }

    public int getSuperhero_id() {
        return superhero_id;
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

    public List<String> getPowers() {
        return powers;
    }

    public void addPower(String superpower_id) {
        powers.add(superpower_id);
    }

    public void setSuperhero_id(int superhero_id) {
        this.superhero_id = superhero_id;
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
}


