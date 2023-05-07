package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
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

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    PacienteService pacienteService;              //Agregado por Claudio el 16/04 - 17:40

    @Autowired
    ProfesionalService profesionalService;
    
    @Autowired
    ObraSocialService obraSocialService;

    
    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/login")
    public String login() {

        return "login.html"; //ver nombre de archivo
    }
    @GetMapping("/mis_profesionales")
    public String mis_profesionales() {
        return "mis_profesionales.html"; //ver nombre de archivo
    }
    

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_PROFESIONAL', 'ROLE_ADMINISTRADOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {

        if (session.getAttribute("pacienteSession") != null) {
            Paciente logueado = (Paciente) session.getAttribute("pacienteSession");
            modelo.put("pacienteSession", logueado);

            if (logueado.getRol().toString().equals("ADMINISTRADOR")) {

                return "redirect:/admin/dashboard";
            }
        }

        if (session.getAttribute("profesionalSession") != null) {
            Profesional logueado = (Profesional) session.getAttribute("profesionalSession");
            modelo.put("profesionalSession", logueado);

            if (logueado.getRol().toString().equals("ADMINISTRADOR")) {
                return "redirect:/admin/dashboard";
            }
        }
        return "inicio.html";
    }

    //FORMULARIO PARA REGISTRAR UN PACIENTE
    @GetMapping("/form_pac")
    public String form_pac(ModelMap model) {
        return "formulario_paciente.html";
    }

    @Transactional
    @PostMapping("/registroPaciente")
    public String registroPaciente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String nacimiento,
            @RequestParam String password, @RequestParam String password2, @RequestParam String direccion,
            @RequestParam String sexo, @RequestParam(required = false) MultipartFile archivo) {
       
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = formato.parse(nacimiento);
            
            pacienteService.crearPaciente(nombre, apellido, dni, correo, telefono, password, password2, direccion, 
                    fechaNacimiento, sexo, archivo);

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
    
    
    @Transactional
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        List<Profesional> profesionales = profesionalService.listar();
        model.addAttribute("profesionales", profesionales);
        return "listar.html";
    }

    @Transactional
    @PostMapping("/buscarespec")
    public String buscarespec(@RequestParam("especialidad") String especialidad, ModelMap model) {
        System.out.println(especialidad);
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidad(especialidad);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("espec", especialidad);
        
        return "listaespecialidad.html";
    }

    @Transactional
    @PostMapping("/buscarespechonorario")
    public String buscarespechonorario(@RequestParam("especialidad") String especialidad, ModelMap model) {
        System.out.println(especialidad);
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidadOrdenadoHonorario(especialidad);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("espec", especialidad);
        return "listaespecialidad.html";
    }

    @Transactional
    @PostMapping("/buscarespeccalificacion")
    public String buscarespeccalificacion(@RequestParam("especialidad") String especialidad, ModelMap model) {
        System.out.println(especialidad);
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidadOrdenadoCalificacion(especialidad);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("espec", especialidad);
        return "listaespecialidad.html";
    }


    @GetMapping("/preguntasFrecuentes")
    public String preguntasFrecuentes() {

        return "preguntas_frecuentes.html"; //ver nombre de archivo
    }
    
    //FORMULARIO PARA CREAR UNA OBRA SOCIAL
     @GetMapping("/form_obraSocial")
    public String form_obraSocial(ModelMap model) {
        
        return "formulario_obra_social.html";
    }

     @Transactional
    @PostMapping("/registroObraSocial")
    public String registroObraSocial(@RequestParam("nombreObraSocial") String nombreObraSocial) {
       
        try {
            obraSocialService.crearObraSocial(nombreObraSocial);
           
            System.out.println("Ingreso de obra social exitoso");
            return "redirect:/admin/dashboard";
            
        } catch (MiExcepcion me) {
            System.out.println("Ingreso de obra social FALLIDO!\n" + me.getMessage());
            
             return "formulario_obra_social.html";
            
        }  
        
    }
    
}