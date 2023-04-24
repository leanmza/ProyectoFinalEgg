package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ariel
 */
@Controller
@RequestMapping("/admin")
public class AdministradorControlador {
    
    @Autowired
    PacienteService pacienteServicio;
    
    @Autowired
    ProfesionalService profesionalServicio;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        
        return "panel.html";
    }
    
    @GetMapping("/pacientes")
    public String mostrarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listar();
        modelo.put("pacientes", pacientes);
        
        return "admin_pacientes.html";
    }
    
    @GetMapping("/profesionales")
    public String mostrarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listar();
        modelo.put("profesionales", profesionales);
        
        return "admin_pacientes.html";
    }
    // /admin/registroProfesional
    
    @GetMapping("/registroProfesional")
    public String registroProfesional(ModelMap modelo) {
        
        return "formulario_profesional.html";
    }
    
   
    @PostMapping("/crearProfesional")
    public String crearProfesional(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String password,
            @RequestParam String password2,  @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios/*,
           @RequestParam("obrasSociales[]") List<String> obrasSociales, @RequestParam("dias[]") List<String> dias,
            @RequestParam("horaInicio") LocalTime horaInicio, @RequestParam ("horaFin") LocalTime horaFin
            , @RequestParam(required = false) List<Turno>turnos*/ ) {
        
        try {
            profesionalServicio.crearProfesional(nombre, apellido,correo,   telefono,   
             password,   password2,  especialidad, ubicacion, modalidad,
             honorarios/*, obrasSociales, dias, horaInicio, horaFin*/);
            
            return "redirect: /admin/profesionales";
            
        } catch (MiExcepcion e) {
            System.out.println("Error al cargar Profesional");
            return "formulario_profesional.html";
        }
       
    }
}
