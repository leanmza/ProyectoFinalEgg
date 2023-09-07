package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Persona;
import com.ProyectoFinal.MedicApp.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {


    @Autowired
    private PersonaService personaService;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenPersona(@PathVariable String id, HttpSession session) {
        
        
        byte[] imagen = null;
        
        if (session.getAttribute("userSession") != null) {
            Persona persona = personaService.getOne(id);
            imagen = persona.getImagen().getContenido();
        } 

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity(imagen, headers, HttpStatus.OK); //retornamos la imagen
        
    }
}
