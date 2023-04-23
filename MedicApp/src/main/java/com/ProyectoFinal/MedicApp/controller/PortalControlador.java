package com.ProyectoFinal.MedicApp.controller;


import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
    
    @GetMapping("/")
    public String index() {

        return "index.html";
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
            
            pacienteService.crearPaciente(nombre, apellido, correo, telefono, password, password2, direccion, fechaNacimiento, sexo);
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
    
    
    
    
    
//    @GetMapping("/registrarProfesional")
//    public String registrarProfesional() {
//        return "formulario_profesional.html"; //ver nombre de archivo
//    }
//    
//    @PostMapping("/regitroProfesional")
//    public String registroProfesional(@RequestParam String nombre, @RequestParam String apellido, 
//            @RequestParam String dni, @RequestParam String email, @RequestParam String telefono,
//            /*MultipartFile archivo,*/ @RequestParam String password, @RequestParam String password2,
//            @RequestParam String especialidad, @RequestParam String modalidad,
//            @RequestParam String ubicacion, /*@RequestParam Date horarioInicio,
//            @RequestParam Date horarioFin,*/@RequestParam Date horario, @RequestParam Date dias,
//            /*@RequestParam List<ObraSocial> obrasSociales, @RequestParam List<Turno> turnos,*/
//            @RequestParam Double honorarios) {
//        try {
//            profesionalService.crearProfesional(nombre, apellido, dni, email, telefono, /*archivo,*/
//                    password, password2, especialidad, modalidad, ubicacion,/* horarioInicio,
//                    horarioFin,*/horario, dias, /*obrasSociales,*/ honorarios);
//            System.out.println("Ingreso exitoso");
//            return "redirec:/inicio";
//        } catch (MiExcepcion me) {
//            System.out.println("Ingreso fallido\n" + me.getMessage());
//            return "formulario_profesional.html";
//        }
//    }
    
    @GetMapping("/login")
    public String login() {
        
        return "login.html"; //ver nombre de archivo
    }


  @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        
        Paciente logueado = (Paciente) session.getAttribute("usuariosession");
        
//        if (logueado.getRol().toString().equals("ADMIN")) {
//            return "redirect:/admin/dashboard";
//        }
        
           return "inicio.html";
    }
}