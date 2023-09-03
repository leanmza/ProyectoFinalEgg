/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Imagen;
import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Persona;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import java.util.ArrayList;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Service.ImagenService;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import com.ProyectoFinal.MedicApp.Service.TurnoService;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cmoro1
 */
@Controller
@RequestMapping("/profesional")
@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL', 'ROLE_ADMINISTRADOR')")
public class ProfesionalControlador {

    @Autowired
    ProfesionalService profesionalService;

    @Autowired
    ObraSocialService obraSocialServicio;

    @Autowired
    ImagenService imagenServicio;

    @Autowired
    TurnoService turnoService;

    ////PERFIL
    @Transactional
    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap modelo, HttpSession obraSocialNueva, @RequestParam(required = false) String error) {
        
        if (error != null && error.equals("errorActualizacion")) {
            String errorMessage = (String) modelo.getAttribute("error");
            modelo.put("error", errorMessage);
        }
        // CARGAMOS LOS DATOS DEL PROFESIONAL
        Profesional profesional = (Profesional) session.getAttribute("userSession");
        // EN EL CASO QUE HAYA AGREGADO UNA OBRA SOCIAL NUEVA, ACTUALIZAMOS LA LISTA DE OBRAS SOCIALES SETEADAS
        if (obraSocialNueva.getAttribute("nuevaObraSocial") != null) {
            String nombreOS = (String) obraSocialNueva.getAttribute("nuevaObraSocial");
            ObraSocial obraSocial = obraSocialServicio.buscarPorNombre(nombreOS);
            profesional.setObraSocial(obraSocial);
        }

        modelo.addAttribute("profesional", profesional);

           // CARGA DE LAS UBICACIONES
         modelo.put("ubicaciones", Ubicacion.values());
  
        // CARGA DE LAS MODALIDADES
        modelo.put("modalidades", Modalidad.values());

        // CARFA DE LAS OBRAS SOCIALES
        List<ObraSocial> obrasSociales = obraSocialServicio.listar();
        modelo.put("obrasSociales", obrasSociales);

        return "editar_profesional.html";
    }

    ////EDITAR PERFIL
    @Transactional
    @PostMapping("/editarPerfil/{id}")
    public String modificarPerfil(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false) String password, @RequestParam(required = false) String password2, @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios,
            @RequestParam String obraSocial, @RequestParam(value = "dias[]", required = false) String[] diasSeleccionados,
            @RequestParam(required = false) String horaInicio, @RequestParam(required = false) String horaFin, /*@RequestParam(required = false) List<Turno>turnos,*/
            HttpSession session, Model modelo) {

        try {

            LocalTime horaInicioLT = LocalTime.parse(horaInicio);
            LocalTime horaFinLT = LocalTime.parse(horaFin);
            
            ObraSocial claseObraSocial = obraSocialServicio.buscarPorNombre(obraSocial); // A PARTIR DEL NOMBRE BUSCAMOS LA CLASE OBRAsOCIAL
            System.out.println("OS: " + claseObraSocial.toString());

            profesionalService.modificarProfesional(id, nombre, apellido, correo, telefono, archivo, password, password2,
                    especialidad, ubicacion, modalidad, honorarios, claseObraSocial, diasSeleccionados, horaInicioLT, horaFinLT);
            
            session.setAttribute("userSession", profesionalService.getOne(id));

            return "redirect:/inicio";

        } catch (MiExcepcion e) {
            System.out.println("Error al actualizar Profesional");
            System.out.println(e.getMessage());
            e.printStackTrace();
            modelo.addAttribute("error", e.getMessage());
            return "redirect:/perfil?error=errorActualizacion";
        }
    }

    
    
//    @Transactional
//    @PostMapping("/buscarEspececialidad")
//    public String buscarEspececialidad(@RequestParam String especialidad, ModelMap model) {
//        System.out.println(especialidad);
//        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidad(especialidad);
//        model.addAttribute("profesionales", profesionales);
//        model.addAttribute("especialidad", especialidad);
//        return "listaespecialidad.html";
//    }


//    // GUARDADO DE DATOS DEL FORMULARIO PROFESIONAL EN UNA SESSION PARA INYECTAR LUEGO DE AGRGAR UNA OBRA SOCIAL NUEVA
//    @Transactional
//    @PostMapping("/guardarDatosFormulario")
//    public String guardarDatosFormulario(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
//            @RequestParam(required = false) String correo, @RequestParam(required = false) String telefono, @RequestParam(required = false) MultipartFile archivo,
//            @RequestParam(required = false, defaultValue = "") String especialidad, @RequestParam(required = false) String ubicacion, @RequestParam(required = false) String modalidad,
//            @RequestParam(required = false) Double honorarios, @RequestParam(required = false) String obraSocial, /*@RequestParam("dias[]") List<String> dias,
//             */ @RequestParam(required = false) String horaInicio, @RequestParam(required = false) String horaFin /*, @RequestParam(required = false) List<Turno>turnos*/,
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
//        System.out.println("Especialidad: " + especialidad);
//        if (!especialidad.isEmpty() || especialidad != null) {
//            profesional.setEspecialidad(especialidad);
//        }
//
//        profesional.setModalidad(modalidad);
//        profesional.setUbicacion(ubicacion);
//        profesional.setHonorario(honorarios);
//        profesional.setObraSocial(ClaseObraSocial);
////        profesional.setDias(dias);
//        if (!horaInicio.isEmpty()) {
//            LocalTime horaInicioLT = LocalTime.parse(horaInicio);
//            profesional.setHoraInicio(horaInicioLT);
//        }
//        if (!horaFin.isEmpty()) {
//            LocalTime horaFinLT = LocalTime.parse(horaFin);
//            profesional.setHoraFin(horaFinLT);
//        }
//
//        sessionFormulario.setAttribute("datosFormulario", profesional);
//
//        return "redirect:/profesional/perfil";
//    }

    ////AGENDA
    @Transactional
    @GetMapping("/agenda")
    public String listaTurnos(ModelMap model, HttpSession session) {

        Profesional profesional = (Profesional) session.getAttribute("userSession");
        String idProfesional = profesional.getId();
        

        List<Turno> turnos = profesionalService.listarTurnos(idProfesional);

        model.addAttribute("turnos", turnos);
        return "agenda.html";
    }

    
    ////ANULAR TURNO
    @Transactional
    @GetMapping("/anularTurno/{id}")
    public String anularTurno(@PathVariable String id) throws MiExcepcion {

        System.out.println("    id" + id);
        turnoService.eliminarTurno(id);
        return "redirect:/profesional/agenda";
    }
}

