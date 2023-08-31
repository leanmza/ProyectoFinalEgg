package com.ProyectoFinal.MedicApp.controller;


import com.ProyectoFinal.MedicApp.entity.Paciente;
import com.ProyectoFinal.MedicApp.entity.Profesional;
import com.ProyectoFinal.MedicApp.service.ImagenService;
import com.ProyectoFinal.MedicApp.service.PacienteService;
import com.ProyectoFinal.MedicApp.service.ProfesionalService;

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
    private ImagenService imagenService;

    @Autowired
    private PacienteService pacienteServicio;
    
    @Autowired
    private ProfesionalService profesionalServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenPersona(@PathVariable String id, HttpSession session) {
        
        
        byte[] imagen = null;
        
        if (session.getAttribute("pacienteSession") != null) {
            System.out.println("Imagen Paciente");
            Paciente paciente = pacienteServicio.getOne(id);
            imagen = paciente.getImagen().getContenido();
        } else if (session.getAttribute("profesionalSession") != null) {
            System.out.println("Imagen Profesional");
            Profesional profesional = profesionalServicio.getOne(id);
            imagen = profesional.getImagen().getContenido();
        } 

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity(imagen, headers, HttpStatus.OK); //retornamos la imagen
        
    }
}
