/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;

import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    PacienteService pacienteService;

    @Autowired
    ObraSocialService obraSocialServicio;

    @Autowired
    ProfesionalService profesionalService;

    //FORMULARIO PARA REGISTRAR UN PACIENTE
    @GetMapping("/registroPaciente")
    public String registroPaciente(ModelMap model, HttpSession sessionFormulario, HttpSession obraSocialNueva) {

        // EN EL CASO QUE HAYA AGREGADO UNA OBRA SOCIAL NUEVA, CARGAMOS LA SESSIONFORMULARIO
        if (sessionFormulario.getAttribute("datosFormulario") != null) {

            Paciente paciente = (Paciente) sessionFormulario.getAttribute("datosFormulario");

            if (obraSocialNueva.getAttribute("nuevaObraSocial") != null) { // Esto se podría eliminar

                String nombreOS = (String) obraSocialNueva.getAttribute("nuevaObraSocial");

                ObraSocial obraSocial = obraSocialServicio.buscarPorNombre(nombreOS);

                paciente.setObraSocial(obraSocial);
            }

            sessionFormulario.setAttribute("datosFormulario", paciente);

            model.put("recargaFormulario", sessionFormulario.getAttribute("datosFormulario"));

        }

        // CARGA DE LAS OBRAS SOCIALES
        List<ObraSocial> obrasSociales = obraSocialServicio.listar();
        model.put("obrasSociales", obrasSociales);

        return "formulario_paciente_wizard.html";
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
        modelo.put("email", correo);
        modelo.put("telefono", telefono);
        modelo.put("nacimiento", nacimiento);
        modelo.put("direccion", direccion);
        modelo.put("sexo", sexo);
        return "formulario_paciente.html";

    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMINISTRADOR')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session, HttpSession obraSocialNueva) {

        // CARGAMOS LOS DATOS DEL PACIENTE
        Paciente paciente = (Paciente) session.getAttribute("userSession");

        // EN EL CASO QUE HAYA AGREGADO UNA OBRA SOCIAL NUEVA, ACTUALIZAMOS LA LISTA DE OBRAS SOCIALES SETEADAS
        if (obraSocialNueva.getAttribute("nuevaObraSocial") != null) {
            String nombreOS = (String) obraSocialNueva.getAttribute("nuevaObraSocial");
            ObraSocial obraSocial = obraSocialServicio.buscarPorNombre(nombreOS);
            paciente.setObraSocial(obraSocial);
        }

        modelo.put("paciente", paciente);

        // CARGA DE LAS OBRAS SOCIALES
        List<ObraSocial> obrasSociales = obraSocialServicio.listar();
        modelo.put("obrasSociales", obrasSociales);

        return "editar_paciente.html";
    }

//////    EDITAR PERFIL
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMINISTRADOR')")
    @PostMapping("/perfil/{id}")

    /////MODIFICAR ESTE MÉTODO, DIVIDIRLO EN 2, UNO DONDE SE PUEDE MODIFICAR LOS DATOS DE INFORMACIÓN Y FOTO DE PERFIL, QUE NO PIDA CONFIRMAR LA CONTRASEÑA
    //////// Y OTRO U OTROS 2 DONDE SE MODIFIQUEN LOS DATOS DE USUARIO EMAIL Y CONTRASEÑA.
    /////// HACER LO MISMO CON PROFESIONAL
    public String modificarPerfil(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String telefono, @RequestParam(required = false) String password, @RequestParam(required = false) String password2,
            @RequestParam String direccion, @RequestParam String sexo, @RequestParam(required = false) MultipartFile archivo, @RequestParam String obraSocial,
            HttpSession session, ModelMap modelo) {

        try {

            pacienteService.modificarPaciente(id, nombre, apellido, direccion, telefono, sexo, obraSocial, password, password2, archivo);

            session.setAttribute("userSession", pacienteService.getOne(id));
            return "redirect:/inicio";

        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());

            return "editar_paciente.html";

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "editar_paciente.html";
        }
    }

    ////BAJA DE PACIENTE
    @Transactional
    @GetMapping("/baja/{id}")
    public String bajaPaciente(@PathVariable String id) {

        pacienteService.darDeBaja(id);

        return "redirect:/";
    }

    ////ALMACENA DATOS DE FORMULARIOS SI SE REFRESCA LA PÁGINA
//    @PostMapping("/guardarDatosFormulario")
//    public String guardarDatosFormulario(MultipartHttpServletRequest formulario, HttpSession sessionFormulario) throws MiExcepcion {
//    
//        Paciente tempPaciente = new Paciente();
//
//        tempPaciente.setNombre(formulario.getParameter("nombre"));
//        tempPaciente.setApellido(formulario.getParameter("apellido"));
//        tempPaciente.setDni(formulario.getParameter("dni"));
//        tempPaciente.setDireccion(formulario.getParameter("direccion"));
//        tempPaciente.setTelefono(formulario.getParameter("telefono"));
//        tempPaciente.setEmail(formulario.getParameter("email"));
//        tempPaciente.setSexo(formulario.getParameter("sexo"));
//    
//        if (!(formulario.getParameter("nacimiento").equals(""))) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate fechaNacimiento = LocalDate.parse(formulario.getParameter("nacimiento"), formatter);
//            tempPaciente.setFechaNacimiento(fechaNacimiento);
//        }
//
//
//        tempPaciente.setPassword(formulario.getParameter("password"));
//
//        sessionFormulario.setAttribute("datosFormulario", tempPaciente);
//
//        return "redirect:/paciente/registroPaciente";
//    }
    ////ALMACENA DATOS DE FORMULARIOS SI SE REFRESCA LA PÁGINA
    @PostMapping("/guardarDatosFormulario")
    public String guardarDatosFormulario(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String dni, @RequestParam(required = false) String email, @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String direccion, @RequestParam(required = false) String fechaNacimiento, @RequestParam(required = false) String sexo,
            HttpSession sessionFormulario) throws MiExcepcion {

        Paciente pacienteTemporal = pacienteService.DatosTemporalesFormulario(nombre, apellido, dni, email, telefono, direccion, fechaNacimiento, sexo);
        sessionFormulario.setAttribute("datosFormulario", pacienteTemporal);

        return "redirect:/paciente/registroPaciente";
    }

////LISTA MIS PROFESIONALES
    @Transactional
    @GetMapping("/misProfesionales")
    public String misProfesionales(ModelMap model, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("userSession");
        String idPaciente = paciente.getId();

        List<String> especialidades = profesionalService.listaEspecialidadesActivas();

        List<Profesional> profesionales = pacienteService.listarProfesionales(idPaciente);

        model.addAttribute("especialidad", especialidades);
        model.addAttribute("profesionales", profesionales);
        return "mis_profesionales.html";
    }

    ////LISTA MIS TURNOS
    @Transactional
    @GetMapping("/misTurnos")
    public String misTurnos(ModelMap model, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("userSession");
        String idPaciente = paciente.getId();

        List<Turno> turnos = pacienteService.listarTurnos(idPaciente);

        model.addAttribute("turnos", turnos);
        return "agenda.html";
    }

    ////CALIFICAR PROFESIONAL
    @Transactional
    @PostMapping("/calificarProfesional/{id}")
    public String calificarProfesional(@PathVariable("id") String id, @RequestParam("puntaje") int puntaje) { //recibe el id del profesional
        System.out.println("puntaje " + puntaje);
        pacienteService.calificarProfesional(id, puntaje);
        return "redirect:/paciente/misProfesionales";
    }

}
