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
    ProfesionalService profesionalService;

    @Autowired
    ObraSocialService obraSocialServicio;

    @Autowired
    ImagenService imagenServicio;

    ////DASHBOARD
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo, @RequestParam(required = false) String exito) {

        if ("registroExitoso".equals(exito)) {
            modelo.put("registroExitoso", "¡Gracias por registrarte en nuestra aplicación! Ahora puedes comenzar a utilizar nuestros servicios");
        }
        
        return "panel.html";
    }


    //LISTA DE PACIENTES
    @GetMapping("/pacientes")  /// NO SE USA ////
    public String mostrarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listar();
        modelo.put("pacientes", pacientes);

        return "listar_paciente.html";
    }


    
//    // GUARDADO DE DATOS DEL FORMULARIO PROFESIONAL EN UNA SESSION PARA INYECTAR LUEGO DE AGRGAR UNA OBRA SOCIAL NUEVA
//    @Transactional
//    @PostMapping("/guardarDatosFormulario")
//    public String guardarDatosFormulario(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
//            @RequestParam(required = false) String correo, @RequestParam(required = false) String telefono, @RequestParam(required = false) MultipartFile archivo,
//            @RequestParam(required = false, defaultValue = "") String especialidad, @RequestParam(required = false) String ubicacion, @RequestParam(required = false) String modalidad,
//            @RequestParam(required = false) Double honorarios, @RequestParam(required = false) String obraSocial, @RequestParam(value = "dias[]", required = false) String[] diasSeleccionados,
//            @RequestParam(required = false) String horaInicio, @RequestParam(required = false) String horaFin /*, @RequestParam(required = false) List<Turno>turnos*/,
//            HttpSession sessionFormulario) throws IOException, MiExcepcion {
//
//        ObraSocial ClaseObraSocial = obraSocialServicio.buscarPorNombre(obraSocial);
//
//        Profesional profesional = new Profesional();
//
//        profesional.setNombre(nombre);
//        profesional.setApellido(apellido);
////        profesional.setDni(dni);
//        profesional.setEmail(correo);
//        profesional.setTelefono(telefono);
//
//        if (!(archivo.isEmpty())) {  //pedimos esto sino nos crea un id para el archivo
//            Imagen imagen = imagenServicio.guardar(archivo);
//            profesional.setImagen(imagen);
//        }
//
//        profesional.setRol(Rol.PROFESIONAL);
//        profesional.setActivo(true);
//        System.out.println("Especialidad: " + especialidad);
//        if (!especialidad.isEmpty() || especialidad != null) {
//            profesional.setEspecialidad(especialidad);
//        }
//
//        profesional.setModalidad(modalidad);
//        profesional.setUbicacion(ubicacion);
//        profesional.setHonorario(honorarios);
//        profesional.setObraSocial(ClaseObraSocial);
//        profesional.setDias(diasSeleccionados);
//        if (!horaInicio.isEmpty()) {
//            LocalTime horaInicioLT = LocalTime.parse(horaInicio);
//            profesional.setHoraInicio(horaInicioLT);
//        }
//        if (!horaFin.isEmpty()) {
//            LocalTime horaFinLT = LocalTime.parse(horaFin);
//            profesional.setHoraFin(horaFinLT);
//        }
//        profesional.setCantVisitas(0);
//        profesional.setPuntaje(0);
//        profesional.setCalificacion(0.0);
//
//        sessionFormulario.setAttribute("datosFormulario", profesional);
//
//        return "redirect:/admin/registroProfesional";
//    }
}
