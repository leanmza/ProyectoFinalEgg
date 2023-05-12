package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Imagen;
import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ImagenService;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    PacienteService pacienteService;              //Agregado por Claudio el 16/04 - 17:40

    @Autowired
    ProfesionalService profesionalService;
    
    @Autowired
    ObraSocialService obraSocialService;
    
    @Autowired
    ImagenService imagenService;

    
    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {

            modelo.put("error", "Lo siento, no hemos podido iniciar sesión con las credenciales que proporcionaste." +
                    " Intentalo nuevamente!");
        }
        return "login.html"; //ver nombre de archivo
    }
    @GetMapping("/mis_profesionales")
    public String mis_profesionales() {
        return "mis_profesionales.html"; //ver nombre de archivo
    }
    

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_PROFESIONAL', 'ROLE_ADMINISTRADOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo, @RequestParam (required = false) String exito) {

        if("turnoExitoso".equals(exito)){
            modelo.put("exito", "¡¡¡El turno se cargo exitosamente!!!");
        }
        
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
    public String form_pac(ModelMap model, HttpSession sessionFormulario, HttpSession obraSocialNueva) {
        
        // EN EL CASO QUE HAYA AGREGADO UNA OBRA SOCIAL NUEVA, CARGAMOS LA SESSIONFORMULARIO
        if (sessionFormulario.getAttribute("datosFormulario") != null) {

            if (obraSocialNueva.getAttribute("nuevaObraSocial") != null) {

                Paciente paciente = (Paciente) sessionFormulario.getAttribute("datosFormulario");
                String nombreOS = (String) obraSocialNueva.getAttribute("nuevaObraSocial");
                ObraSocial obraSocial = obraSocialService.buscarPorNombre(nombreOS);
                paciente.setObraSocial(obraSocial);
                sessionFormulario.setAttribute("datosFormulario", paciente);
            }
            model.put("recargaFormulario", sessionFormulario.getAttribute("datosFormulario"));
        }
        
        // CARFA DE LAS OBRAS SOCIALES
        List<ObraSocial> obrasSociales = obraSocialService.listar();
        model.put("obrasSociales", obrasSociales);
        
        return "formulario_paciente.html";
    }

    @Transactional
    @PostMapping("/registroPaciente")
    public String registroPaciente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni,
                                   @RequestParam String correo, @RequestParam String telefono, @RequestParam String nacimiento,
                                   @RequestParam String password, @RequestParam String password2, @RequestParam String direccion,
                                   @RequestParam(required = false) String sexo, @RequestParam(required = false) MultipartFile archivo,
                                   @RequestParam String obraSocial, ModelMap modelo, HttpSession session) {

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = formato.parse(nacimiento);
            if (sexo == null) {
                sexo = "No especificado";
            }
            
            ObraSocial ClaseObraSocial = obraSocialService.getOne(obraSocial);
            
            pacienteService.crearPaciente(nombre, apellido, dni, correo, telefono, password, password2, direccion,
                    fechaNacimiento, sexo, archivo, ClaseObraSocial);

            modelo.put("exito", "¡Gracias por registrarte en nuestra aplicación! Ahora puedes comenzar a utilizar nuestros servicios");


        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            modelo.put("error", me.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("correo", correo);
            modelo.put("telefono", telefono);
            modelo.put("nacimiento", nacimiento);
            modelo.put("direccion", direccion);
            modelo.put("sexo", sexo);
            return "formulario_paciente.html";

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            modelo.put("error", "La fecha ingresada es incorrecta, verifica que esté en formato DD/MM/AAAA");
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("correo", correo);
            modelo.put("telefono", telefono);
            modelo.put("nacimiento", nacimiento);
            modelo.put("direccion", direccion);
            modelo.put("sexo", sexo);
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

//    @Transactional
//    @PostMapping("/buscarespec")
//    public String buscarespec(@RequestParam("especialidad") String especialidad, ModelMap model) {
//        System.out.println(especialidad);
//        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidad(especialidad);
//        model.addAttribute("profesionales", profesionales);
//        model.addAttribute("espec", especialidad);
//
//        return "listaespecialidad.html";
//    }

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

    @Transactional
    @PostMapping("/guardarDatosFormulario")
    public String guardarDatosFormulario (@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String dni, @RequestParam(required = false) String correo, @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String password, @RequestParam(required = false) String password2, @RequestParam(required = false) String direccion,
            @RequestParam(required = false) String nacimiento, @RequestParam(required = false) String sexo,
            @RequestParam(required = false) MultipartFile archivo, @RequestParam(required = false) String obraSocial, HttpSession sessionFormulario) throws MiExcepcion {
        
        try {
            ObraSocial ClaseObraSocial = obraSocialService.buscarPorNombre(obraSocial);

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd
            Date fechaNacimiento = formato.parse(nacimiento);


            Paciente paciente = new Paciente();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setEmail(correo);
            paciente.setTelefono(telefono);
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));
            paciente.setDireccion(direccion);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setSexo(sexo);
            paciente.setObraSocial(ClaseObraSocial);

            if(!(archivo.isEmpty())) {  //pedimos esto sino nos crea un id para el archivo
                Imagen imagen = imagenService.guardar(archivo);
                paciente.setImagen(imagen);
            }

            sessionFormulario.setAttribute("datosFormulario", paciente);
        
        } catch (ParseException ex) {
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/form_pac";
    }
}