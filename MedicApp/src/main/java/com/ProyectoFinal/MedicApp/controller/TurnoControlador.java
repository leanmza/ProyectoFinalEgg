/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import com.ProyectoFinal.MedicApp.Service.TurnoService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Lean
 */
@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    TurnoService turnoService;

    @Autowired
    ProfesionalService profesionalService;

    ////TURNERO DESDE LA LISTA DE PROFESIONALES
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/formularioTurno/{idProfesional}")
    public String turno(@PathVariable String idProfesional, Model model) throws MiExcepcion {

        Profesional profesional = profesionalService.getOne(idProfesional); //Busco el profesional 

        model.addAttribute("profesional", profesional); //Agrego al profesional al model

        return "formulario_turno.html";
    }

////TURNERO DESDE EL HEADER 
//    MODIFICAR PARA QUE SEA UN SOLO GET PARA LOS TURNOS
    @GetMapping("/formularioTurnoHeader")
    public String turno(ModelMap model) {

        List<Profesional> profesionales = profesionalService.listar(); //Se usa en el modal que se abre 

        model.addAttribute("profesionales", profesionales);

        return "formulario_turno_header.html";
    }

    ///REGISTRO DE TURNO
    @Transactional
    @PostMapping("/registroTurno")
    public String registroTurno(@ModelAttribute Profesional pro, @RequestParam String motivo,
            @RequestParam String dia, @RequestParam String horario, HttpSession session, ModelMap modelo)
            throws MiExcepcion {

        Paciente paciente = (Paciente) session.getAttribute("userSession");

        String idProfesional = pro.getId();

        Profesional profesional = profesionalService.getOne(idProfesional);

        try {

            turnoService.crearTurno(profesional, paciente, dia, horario, motivo);

            System.out.println("Turno exitoso");
            return "redirect:/inicio?exito=turnoExitoso";

        } catch (MiExcepcion me) {
            System.out.println("Registro de turno FALLIDO!\n" + me.getMessage());
            return "formulario_turno.html";

        }

    }

    ////ANULAR TURNO
    @Transactional
    @GetMapping("/anularTurno/{id}")
    public String anularTurno(@PathVariable String id) throws MiExcepcion {

        turnoService.eliminarTurno(id);
        return "redirect:/profesional/agenda";
    }

}
