package com.example.superherov5.controllers;

import com.example.superherov5.dto.SuperheroAddToDatabaseDTO;
import com.example.superherov5.dto.SuperheroDTO;
import com.example.superherov5.repositories.SuperheroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("superhero")
@Controller
public class Superherocontroller_DB {
    @Autowired
    private SuperheroRepo superheroRepo;

    @GetMapping("herolist")
    public String heroList(Model model) {
        List<SuperheroDTO> heroList = superheroRepo.getSuperheroes();
        model.addAttribute("heroList", heroList);
        return "index"; // This should be the name of your Thymeleaf template
    }

    @GetMapping(path = "powers{superhero_id}")
    public String powers(Model model, @RequestParam("superhero_id") String superhero_id) {
        SuperheroDTO hero = superheroRepo.getSuperhero(superhero_id);
        model.addAttribute("hero", hero);
        return "powers";
    }

    @GetMapping("/add")
    public String showAddSuperheroForm(Model model) {
        // Create a new SuperheroAddToDatabaseDTO object and add it to the model
        model.addAttribute("superhero", new SuperheroAddToDatabaseDTO());

        // Retrieve the lists of cities and powers from the database
        List<String> cities = superheroRepo.getCities();
        List<String> powers = superheroRepo.getPowers();

        // Add the lists to the model
        model.addAttribute("cities", cities);
        model.addAttribute("powers", powers);

        // Return the name of the Thymeleaf template that displays the add superhero form
        return "addSuperheroForm";
    }

    @PostMapping("/add")
    public String addSuperhero(@ModelAttribute("superheroDto") SuperheroAddToDatabaseDTO superheroDto) {
        superheroRepo.addSuperheroToDatabase(superheroDto);
        return "redirect:/herolist";
    }



}