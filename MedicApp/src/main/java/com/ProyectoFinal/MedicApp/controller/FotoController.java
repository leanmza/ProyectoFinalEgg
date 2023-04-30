package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Service.FotoService;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
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
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @Autowired
    private PacienteService pacienteServicio;
    
    @Autowired
    private ProfesionalService profesionalServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id, HttpSession session) {
        byte[] foto = null;
        
        if (session.getAttribute("pacienteSession") != null) {
            Paciente paciente = pacienteServicio.getOne(id);
            foto = paciente.getFoto().getContenido();
        }
        
        if (session.getAttribute("profesionalSession") != null) {
            Profesional profesional = profesionalServicio.getOne(id);
            foto = profesional.getFoto().getContenido();
        }

            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = new MediaType("IMAGE_JPEG", "IMAGE_PNG");
            headers.setContentType(mediaType);

            return new ResponseEntity(foto, headers, HttpStatus.OK); //retornamos la imagen
        
    }
}
