package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Imagen;
import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Persona;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ImagenService;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    ////LOGIN
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, @RequestParam(required = false) String exito) {

        if ("registroExitoso".equals(exito)) {
            modelo.put("registroExitoso", "¡Gracias por registrarte en nuestra aplicación! Ahora puedes comenzar a utilizar nuestros servicios");
        }

        if (error != null) {
            modelo.put("error", "Lo siento, no hemos podido iniciar sesión con las credenciales que proporcionaste."
                    + " Intentalo nuevamente!");
        }
        return "login.html"; //ver nombre de archivo
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_PROFESIONAL', 'ROLE_ADMINISTRADOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo, @RequestParam(required = false) String exito) {

        if ("turnoExitoso".equals(exito)) {
            modelo.put("exito", "¡¡¡El turno se cargo exitosamente!!!");
        }

        if (session.getAttribute("userSession") != null) {
            Persona logueado = (Persona) session.getAttribute("userSession");
            modelo.put("userSession", logueado);

            if (logueado.getRol().toString().equals("ADMINISTRADOR")) {
                return "redirect:/admin/dashboard";
            }
        }

        return "inicio.html";
    }

    
    ////LISTA DE PROFESIONALES HEADER
    @Transactional
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        List<Profesional> profesionales = profesionalService.listar();
        model.addAttribute("profesionales", profesionales);
        return "listar.html";
    }

//    @Transactional
//    @PostMapping("/buscarespechonorario")
//    public String buscarespechonorario(@RequestParam("especialidad") String especialidad, ModelMap model) {
//        System.out.println(especialidad);
//        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidadOrdenadoHonorario(especialidad);
//        model.addAttribute("profesionales", profesionales);
//        model.addAttribute("espec", especialidad);
//        return "listaespecialidad.html";
//    }
//
//    @Transactional
//    @PostMapping("/buscarespeccalificacion")
//    public String buscarespeccalificacion(@RequestParam("especialidad") String especialidad, ModelMap model) {
//        System.out.println(especialidad);
//        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidadOrdenadoCalificacion(especialidad);
//        model.addAttribute("profesionales", profesionales);
//        model.addAttribute("espec", especialidad);
//        return "listaespecialidad.html";
//    }
    ////PREGUNTAS FRECUENTES
    @GetMapping("/preguntasFrecuentes")
    public String preguntasFrecuentes() {

        return "preguntas_frecuentes.html"; //ver nombre de archivo
    }

    //FORMULARIO PARA CREAR UNA OBRA SOCIAL
    @GetMapping("/form_obraSocial")
    public String form_obraSocial(ModelMap model) {

        return "formulario_obra_social.html";
    }

    // GUARDADO DE OBRA SOCIAL NUEVA
    @Transactional
    @PostMapping("/registroObraSocial")
    public String registroObraSocial(@RequestParam("nombreObraSocial") String nombreObraSocial, HttpSession obraSocialNueva) {

        try {
            obraSocialService.crearObraSocial(nombreObraSocial);

            System.out.println("Ingreso de obra social exitoso");
            obraSocialNueva.setAttribute("nuevaObraSocial", nombreObraSocial);
            return "formulario_obra_social.html";

        } catch (MiExcepcion me) {
            System.out.println("Ingreso de obra social FALLIDO!\n" + me.getMessage());

            return "formulario_obra_social.html";

        }
    }

}
