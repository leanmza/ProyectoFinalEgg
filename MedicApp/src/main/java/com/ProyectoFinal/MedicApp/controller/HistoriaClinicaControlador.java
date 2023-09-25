/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.HistoriaClinica;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.HistoriaClinicaService;
import java.util.Date;
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

/**
 *
 * @author Lean
 */
@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_PROFESIONAL')")
@RequestMapping("/historiaClinica")
public class HistoriaClinicaControlador {

    @Autowired
    HistoriaClinicaService historiaClinicaService;

    ////CREAR HISTORIA
    @Transactional
    @GetMapping("/registrarHistoriaClinica")
    public String crearHistoriaClinica(ModelMap modelo, HttpSession session) {

        Profesional profesional = (Profesional) session.getAttribute("userSession");
        modelo.put("profesional", profesional);

        modelo.put("dia", new Date());
        return "formulario_historia_clinica.html";
    }

    ////REGISTRAR HISTORIA
    @Transactional
    @PostMapping("/registroHistoriaClinica")
    public String registroPaciente(@RequestParam String dni, @RequestParam String fecha,
            @RequestParam String diagnostico, @RequestParam String tratamiento, HttpSession session, ModelMap modelo) {

        Profesional profesional = (Profesional) session.getAttribute("userSession");

        try {
            historiaClinicaService.crearHistoriaClinica(dni, fecha, profesional, diagnostico, tratamiento);

            modelo.put("exito", "Ingreso de historia clinica exitoso");
            return "redirect:/inicio";

        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            modelo.put("error", "Ingreso de paciente FALLIDO!\n" + me.getMessage());
            return "formulario_historia_clinica.html";

        }
    }

    ////BUSCAR HISTORIA
    @GetMapping("/buscar")
    public String listar(ModelMap model) {

        return "busqueda_historia_clinica.html";
    }

    ////LISTA DE HISTORIAS
    @Transactional
    @PostMapping("/listar")
    public String listar(@RequestParam String dniPaciente, ModelMap model) {

   
        List<HistoriaClinica> historiasClinicas = historiaClinicaService.listar(dniPaciente);

        model.addAttribute("historiasClinicas", historiasClinicas);
        return "lista_historia_clinica.html";
    }

}
