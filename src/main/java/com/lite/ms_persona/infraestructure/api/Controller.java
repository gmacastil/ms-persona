package com.lite.ms_persona.infraestructure.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class Controller {

    @GetMapping ("/create-persona")
    public String createPersona() {
        // Implement the logic to create a persona
        return "Persona created successfully";
    }
    
}
