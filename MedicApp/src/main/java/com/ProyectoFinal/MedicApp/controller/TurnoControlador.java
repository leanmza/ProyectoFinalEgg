/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.entity.Paciente;
import com.ProyectoFinal.MedicApp.entity.Profesional;
import com.ProyectoFinal.MedicApp.exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.service.PacienteService;
import com.ProyectoFinal.MedicApp.service.ProfesionalService;
import com.ProyectoFinal.MedicApp.service.TurnoService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    PacienteRepositorio pacienteRepositorio;
    @Autowired
    TurnoService turnoService;

    @Autowired
    ProfesionalService profesionalService;

    ////TURNERO DESDE LA LISTA DE PROFESIONALES
    @GetMapping("/formularioTurno/{idProfesional}")
    public String turno(@PathVariable String idProfesional, Model model) {

        Optional<Profesional> respuesta = profesionalRepositorio.findById(idProfesional);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            model.addAttribute("profesional", profesional);
            System.out.println("profesional" + profesional);
        }

        return "formulario_turno.html";
    }

////TURNERO DESDE EL HEADER
    @GetMapping("/formularioTurnoHeader")
    public String turno(ModelMap model) {
        List<Profesional> profesionales = profesionalService.listar();
        model.addAttribute("profesionales", profesionales);
        return "formulario_turno_header.html";
    }

    ///REGISTRO DE TURNO
    @Transactional
    @PostMapping("/registroTurno")
    public String registroTurno(@ModelAttribute Profesional pro, @RequestParam String motivo,
            @RequestParam String dia, @RequestParam String horario, HttpSession session,
            ModelMap modelo) throws MiExcepcion {

        Paciente paciente = (Paciente) session.getAttribute("userSession");

        String idProfesional = pro.getId();

        Profesional profesional = profesionalRepositorio.findById(idProfesional).orElse(null);

        System.out.println("idPro " + idProfesional);
        System.out.println("");
        System.out.println(horario);
        try {

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha = formato.parse(dia);
            System.out.println("fecha " + fecha);

            LocalTime hora = LocalTime.parse(horario);

            turnoService.crearTurno(profesional, paciente, fecha, hora, motivo);

            System.out.println("Turno exitoso");
            return "redirect:/inicio?exito=turnoExitoso";

        } catch (MiExcepcion me) {
            System.out.println("Registro de turno FALLIDO!\n" + me.getMessage());
            return "formulario_turno.html";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "formulario_turno.html";

    }

}
