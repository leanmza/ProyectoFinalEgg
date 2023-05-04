package com.ProyectoFinal.MedicApp.controller;


import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    PacienteService pacienteService;              //Agregado por Claudio el 16/04 - 17:40

    @Autowired
    ProfesionalService profesionalService;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/login")
    public String login() {

        return "login.html"; //ver nombre de archivo
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
             @RequestParam(required = false) String sexo, @RequestParam(required = false) MultipartFile archivo, ModelMap modelo, HttpSession session) {

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = formato.parse(nacimiento);

            if (sexo == null) {
                sexo = "No especificado";
            }

            pacienteService.crearPaciente(nombre, apellido, dni, correo, telefono, password, password2, direccion,
                    fechaNacimiento, sexo, archivo);

            modelo.put("exito","¡Gracias por registrarte en nuestra aplicación! Ahora puedes comenzar a utilizar nuestros servicios");




        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            modelo.put("error", me.getMessage());
            return "formulario_paciente.html";

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            modelo.put("error", "La fecha ingresada es incorrecta, verifica que esté en formato DD/MM/AAAA");
            return "formulario_paciente.html";
        }

        if (session.getAttribute("pacienteSession") != null) {
            Paciente logueado = (Paciente) session.getAttribute("pacienteSession");
            modelo.put("pacienteSession", logueado);

            if (logueado.getRol().toString().equals("ADMINISTRADOR")) {

                return "redirect:/inicio";
            }
        }
        return "login.html";


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

}