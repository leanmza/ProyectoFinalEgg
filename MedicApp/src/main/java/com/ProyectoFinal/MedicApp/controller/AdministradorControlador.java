package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Service.ImagenService;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
   

}
