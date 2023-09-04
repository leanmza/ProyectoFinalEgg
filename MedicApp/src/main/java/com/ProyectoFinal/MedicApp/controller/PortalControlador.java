package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Imagen;
import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Persona;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
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

    @Autowired
    ImagenService imagenService;

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

//            if (logueado.getRol().toString().equals("ADMINISTRADOR")) {
//
//                return "redirect:/admin/dashboard";
//            }
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
            @RequestParam(required = false) String obraSocial, ModelMap modelo, HttpSession session) throws ParseException {

        try {
            if (sexo == null) {
                sexo = "No especificado";
            }

            pacienteService.crearPaciente(nombre, apellido, dni, correo, direccion, telefono, nacimiento, sexo,
                    obraSocial, password, password2, archivo);

            return "redirect:/inicio?exito=registroExitoso";

        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            modelo.put("error", me.getMessage());
        }

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

    ////ALMACENA DATOS DE FORMULARIOS SI SE REFRESCA LA PÁGINA
    @Transactional
    @PostMapping("/guardarDatosFormulario")
    public String guardarDatosFormulario(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String dni, @RequestParam(required = false) String correo, @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String password, @RequestParam(required = false) String password2, @RequestParam(required = false) String direccion,
            @RequestParam(required = false) String nacimiento, @RequestParam(required = false) String sexo,
            @RequestParam(required = false) MultipartFile archivo, @RequestParam(required = false) String obraSocial, HttpSession sessionFormulario) throws MiExcepcion {

    
            ObraSocial ClaseObraSocial = obraSocialService.buscarPorNombre(obraSocial);

            Paciente paciente = new Paciente();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setEmail(correo);
            paciente.setTelefono(telefono);
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));
            paciente.setDireccion(direccion);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaDeNacimiento = LocalDate.parse(nacimiento, formatter);
            paciente.setFechaNacimiento(fechaDeNacimiento);
            paciente.setSexo(sexo);
            paciente.setObraSocial(ClaseObraSocial);

            if (!(archivo.isEmpty())) {  //pedimos esto sino nos crea un id para el archivo
                Imagen imagen = imagenService.guardar(archivo);
                paciente.setImagen(imagen);
            }

            sessionFormulario.setAttribute("datosFormulario", paciente);

 
        return "redirect:/form_pac";
    }
}
