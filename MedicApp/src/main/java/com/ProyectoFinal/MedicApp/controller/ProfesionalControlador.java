/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author cmoro1
 */

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/pro")
public class ProfesionalControlador {
    
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    ProfesionalService profesionalService;



    

 @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Profesional profesional = (Profesional) session.getAttribute("profesionalSession");
        modelo.put("profesional", profesional);
        
        return "editar_profesional.html";
    }
        
    
    @PostMapping("/perfil/{id}")
    public String modificarPerfil(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String password,
            @RequestParam String password2,  @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios,/*,
           @RequestParam("obrasSociales[]") List<String> obrasSociales, @RequestParam("dias[]") List<String> dias,
            @RequestParam("horaInicio") LocalTime horaInicio, @RequestParam ("horaFin") LocalTime horaFin
            , @RequestParam(required = false) List<Turno>turnos*/  HttpSession session, ModelMap modelo ) {
        
        try {
    
            profesionalService.modificarProfesional(modalidad, nombre, apellido, correo, telefono, password, password2,
                    especialidad, ubicacion, modalidad, honorarios);
       
            session.setAttribute("profesionalSession", profesionalService.getOne(id));
            return "redirect:/inicio";
            
        } catch (MiExcepcion me) {
            System.out.println("Ingreso de profesional FALLIDO!\n" + me.getMessage());
            
            return "editar_profesional.html";
            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "editar_profesional.html";
        }
    }
}
