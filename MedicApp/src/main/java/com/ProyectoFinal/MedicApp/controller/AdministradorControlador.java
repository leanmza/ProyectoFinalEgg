package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
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
    
    @GetMapping("/form_pac")
    public String form_pac(ModelMap model) {
        return "formulario_paciente.html";
    }

    @PostMapping("/registroPaciente")
    public String registroPaciente(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String nacimiento,
            @RequestParam String password, @RequestParam String password2, @RequestParam String direccion,
            @RequestParam String sexo) {
       
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = formato.parse(nacimiento);
            
            pacienteServicio.crearPaciente(nombre, apellido, correo, telefono, password, password2, direccion, fechaNacimiento, sexo);
            System.out.println("Ingreso de paciente exitoso");
            return "redirect:/inicio";
            
        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            
            return "formulario_paciente.html";
            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "formulario_paciente.html";
        }
    }
    
    
    
    @GetMapping("/pacientes")
    public String mostrarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listar();
        modelo.put("pacientes", pacientes);
        
        return "listar_paciente.html";
    }
    
    @GetMapping("/profesionales")
    public String mostrarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listar();
        modelo.put("profesionales", profesionales);
        
        return "listar.html";
    }
    // /admin/registroProfesional
    

    @GetMapping("/form_pro")
    public String form_pro(ModelMap model) {
        return "formulario_profesional.html";
    }
    
    @GetMapping("/registroProfesional")
    public String registroProfesional(ModelMap modelo) {
        
        List<String> ubicaciones = new ArrayList<>();
        for (Ubicacion aux : Ubicacion.values()) {
            ubicaciones.add(aux.toString());
        }
        modelo.put("ubicaciones", ubicaciones);
        
        List<String> modalidades = new ArrayList<>();
        for (Modalidad aux : Modalidad.values()) {
            modalidades.add(aux.toString());
        }
        modelo.put("modalidades", modalidades);
        
        return "formulario_profesional.html";
    }
    
   
    @PostMapping("/crearProfesional")
    public String crearProfesional(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String password,
            @RequestParam String password2,  @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios,/*
           @RequestParam("obrasSociales[]") List<String> obrasSociales, @RequestParam("dias[]") List<String> dias,
            */@RequestParam LocalTime horaInicio, @RequestParam LocalTime horaFin
            /*, @RequestParam(required = false) List<Turno>turnos*/ ) {
        
        try {
            profesionalServicio.crearProfesional(nombre, apellido,correo,   telefono,   
             password,   password2,  especialidad, ubicacion, modalidad,
             honorarios/*, obrasSociales, dias*/, horaInicio, horaFin);
            
            return "redirect:/admin/profesionales";
            
        } catch (MiExcepcion e) {
            System.out.println("Error al cargar Profesional");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "formulario_profesional.html";
        }
       
    }
}
