package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    
       @Autowired
    ObraSocialService obraSocialServicio;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo() {

        return "panel.html";
    }
    
    
    //FORMULARIO PARA CREAR UN PACIENTE

    
    
    //LISTA DE PACIENTES
    @GetMapping("/pacientes")
    public String mostrarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listar();
        modelo.put("pacientes", pacientes);

        return "listar_paciente.html";
    }
    
    //LISTA DE PROFESIONALES
    @GetMapping("/profesionales")
    public String mostrarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listar();
        modelo.put("profesionales", profesionales);

        return "listar.html";
    }

   
    //FORMULARIO PARA CREAR UN PROFESIONAL
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
            @RequestParam String correo, @RequestParam String telefono, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam String password,@RequestParam String password2, @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios,/*
           @RequestParam("obrasSociales[]") List<String> obrasSociales, @RequestParam("dias[]") List<String> dias,
             */ @RequestParam String horaInicio, @RequestParam String horaFin
    /*, @RequestParam(required = false) List<Turno>turnos*/) {

        try {
            
            LocalTime horaInicioLT = LocalTime.parse(horaInicio);
            LocalTime horaFinLT = LocalTime.parse(horaFin);
            
            profesionalServicio.crearProfesional(nombre, apellido, correo, telefono,
                    archivo, password, password2, especialidad, ubicacion, modalidad,
                    honorarios/*, obrasSociales, dias*/, horaInicioLT, horaFinLT);

            return "redirect:/admin/profesionales";

        } catch (MiExcepcion e) {
            System.out.println("Error al cargar Profesional");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "formulario_profesional.html";
        }

    }
    
    
    
    //FORMULARIO PARA CREAR UNA OBRA SOCIAL
     @GetMapping("/form_obraSocial")
    public String form_obraSocial(ModelMap model) {
        
        return "formulario_obra_social.html";
    }

     @Transactional
    @PostMapping("/registroObraSocial")
    public String registroObraSocial(@RequestParam String nombreObraSocial) {
       
        try {
            obraSocialServicio.crearObraSocial(nombreObraSocial);
           
            System.out.println("Ingreso de obra social exitoso");
            return "redirect:/admin/dashboard";
            
        } catch (MiExcepcion me) {
            System.out.println("Ingreso de obra social FALLIDO!\n" + me.getMessage());
            
             return "formulario_obra_social.html";
            
        }  
        
    }
    

}
