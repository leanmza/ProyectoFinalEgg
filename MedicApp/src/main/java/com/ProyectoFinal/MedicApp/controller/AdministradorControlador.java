package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Imagen;
import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ImagenService;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Autowired
    ImagenService imagenServicio;

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
    public String registroProfesional(ModelMap modelo, HttpSession sessionFormulario, HttpSession obraSocialNueva) {

        // EN EL CASO QUE HAYA AGREGADO UNA OBRA SOCIAL NUEVA, CARGAMOS LA SESSIONFORMULARIO
        if (sessionFormulario.getAttribute("datosFormulario") != null) {

            if (obraSocialNueva.getAttribute("nuevaObraSocial") != null) {

                Profesional profesional = (Profesional) sessionFormulario.getAttribute("datosFormulario");
                String nombreOS = (String) obraSocialNueva.getAttribute("nuevaObraSocial");
                ObraSocial obraSocial = obraSocialServicio.buscarPorNombre(nombreOS);
                profesional.setObraSocial(obraSocial);
                sessionFormulario.setAttribute("datosFormulario", profesional);
            }
            modelo.put("recargaFormulario", sessionFormulario.getAttribute("datosFormulario"));
        }

        // CARGA DE LAS UBICACIONES
        List<String> ubicaciones = new ArrayList<>();
        for (Ubicacion aux : Ubicacion.values()) {
            ubicaciones.add(aux.toString());
        }
        modelo.put("ubicaciones", ubicaciones);

        // CARGA DE LAS MODALIDADES
        List<String> modalidades = new ArrayList<>();
        for (Modalidad aux : Modalidad.values()) {
            modalidades.add(aux.toString());
        }
        modelo.put("modalidades", modalidades);

        // CARFA DE LAS OBRAS SOCIALES
        List<ObraSocial> obrasSociales = obraSocialServicio.listar();
        modelo.put("obrasSociales", obrasSociales);

        return "formulario_profesional.html";
    }

    @PostMapping("/crearProfesional")
    public String crearProfesional(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam String password, @RequestParam String password2, @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios,
            @RequestParam String obraSocial, /*@RequestParam("dias[]") List<String> dias,
             */ @RequestParam String horaInicio, @RequestParam String horaFin
    /*, @RequestParam(required = false) List<Turno>turnos*/,ModelMap modelo) throws IOException {

        try {

            LocalTime horaInicioLT = LocalTime.parse(horaInicio);
            LocalTime horaFinLT = LocalTime.parse(horaFin);

            ObraSocial claseObraSocial = obraSocialServicio.getOne(obraSocial);

            System.out.println(archivo.getBytes().toString());
            profesionalServicio.crearProfesional(nombre, apellido, correo, telefono,
                    archivo, password, password2, especialidad, ubicacion, modalidad,
                    honorarios, claseObraSocial/*, dias*/, horaInicioLT, horaFinLT);

            modelo.put("exito","¡Felicidades! Tu registro como profesional ha sido exitoso.");

            return "redirect:/admin/dashboard";

        } catch (MiExcepcion e) {
            System.out.println("Error al cargar Profesional");
            System.out.println(e.getMessage());
            e.printStackTrace();
            modelo.put("error", e.getMessage());
            return "formulario_profesional.html";
        }

    }
    
    // GUARDADO DE DATOS DEL FORMULARIO PROFESIONAL EN UNA SESSION PARA INYECTAR LUEGO DE AGRGAR UNA OBRA SOCIAL NUEVA
    @Transactional
    @PostMapping("/guardarDatosFormulario")
    public String guardarDatosFormulario(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String correo, @RequestParam(required = false) String telefono, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false, defaultValue = "") String especialidad, @RequestParam(required = false) String ubicacion, @RequestParam(required = false) String modalidad,
            @RequestParam(required = false) Double honorarios, @RequestParam(required = false) String obraSocial, /*@RequestParam("dias[]") List<String> dias,
             */ @RequestParam(required = false) String horaInicio, @RequestParam(required = false) String horaFin /*, @RequestParam(required = false) List<Turno>turnos*/,
             HttpSession sessionFormulario) throws IOException, MiExcepcion {

        ObraSocial ClaseObraSocial = obraSocialServicio.buscarPorNombre(obraSocial);

        Profesional profesional = new Profesional();

        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
//        profesional.setDni(dni);
        profesional.setEmail(correo);
        profesional.setTelefono(telefono);

        if (!(archivo.isEmpty())) {  //pedimos esto sino nos crea un id para el archivo
            Imagen imagen = imagenServicio.guardar(archivo);
            profesional.setImagen(imagen);
        }

        profesional.setRol(Rol.PROFESIONAL);
        profesional.setActivo(true);
        System.out.println("Especialidad: " + especialidad);
        if (!especialidad.isEmpty() || especialidad != null) {
            profesional.setEspecialidad(especialidad);
        }

        profesional.setModalidad(modalidad);
        profesional.setUbicacion(ubicacion);
        profesional.setHonorario(honorarios);
        profesional.setObraSocial(ClaseObraSocial);
//        profesional.setDias(dias);
        if (!horaInicio.isEmpty()) {
            LocalTime horaInicioLT = LocalTime.parse(horaInicio);
            profesional.setHoraInicio(horaInicioLT);
        }
        if (!horaFin.isEmpty()) {
            LocalTime horaFinLT = LocalTime.parse(horaFin);
            profesional.setHoraFin(horaFinLT);
        }
        profesional.setCantVisitas(0);
        profesional.setPuntaje(0);
        profesional.setCalificacion(0.0);

        sessionFormulario.setAttribute("datosFormulario", profesional);

        return "redirect:/admin/registroProfesional";
    }
}
