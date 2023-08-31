/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.entity.HistoriaClinica;
import com.ProyectoFinal.MedicApp.entity.Paciente;
import com.ProyectoFinal.MedicApp.entity.Profesional;
import com.ProyectoFinal.MedicApp.exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.repository.HistoriaClinicaRepositorio;
import com.ProyectoFinal.MedicApp.service.HistoriaClinicaService;
import com.ProyectoFinal.MedicApp.service.PacienteService;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    HistoriaClinicaRepositorio historiaClinicaRepositorio;

    @Autowired
    HistoriaClinicaService historiaClinicaService;
    
    @Autowired
    PacienteService pacienteServicio;

    ////BUSCAR HISTORIA
    @GetMapping("/buscar")
    public String listar(ModelMap model) {

        return "busqueda_historia_clinica.html";
    }
    
    
    ////LISTA DE HISTORIAS
    @Transactional
    @PostMapping("/listar")
    public String listar(@RequestParam String dniPaciente, ModelMap model) {
        
        System.out.println(dniPaciente);
        
        List<HistoriaClinica> historiasClinicas = historiaClinicaService.listar(dniPaciente);
        
        model.addAttribute("historiasClinicas", historiasClinicas);
        return "lista_historia_clinica.html";
    }
    
    
    ////CREAR HISTORIA
    @Transactional
    @GetMapping("/form_historia_clinica")
    public String crearHistoriaClinica(ModelMap modelo, HttpSession session) {
        
        Profesional profesional = (Profesional) session.getAttribute("profesionalSession");
        modelo.put("profesional", profesional);

        modelo.put("dia", new Date());
        return "formulario_historia_clinica.html";
    }
    
    
    ////REGISTRAR HISTORIA
    @Transactional
    @PostMapping("/registroHistoriaClinica")
    public String registroPaciente(@RequestParam String dni, @RequestParam String fecha,
            @RequestParam String diagnostico, HttpSession session, ModelMap modelo) {

        Paciente paciente = pacienteServicio.buscarPorDni(dni);
        Profesional profesional = (Profesional) session.getAttribute("profesionalSession");
        
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaConsulta = formato.parse(fecha);

            //MODIFICAR EL SERVICE crearHistoriaClinica PARA QUE RECIBA EL DNI Y LO USE PARA BUSCAR EN EL REPOSITORIO DE LOS PACIENTES, 
            //TRAIGA AL PACIENTE Y LOS ASIGNE A ESTA HISTORIA CLINICA
            historiaClinicaService.crearHistoriaClinica(paciente, fechaConsulta, profesional, diagnostico);
            System.out.println("Ingreso de historia clinica exitoso");
            modelo.put("exito","Ingreso de historia clinica exitoso");
            return "redirect:/inicio";

        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            modelo.put("error","Ingreso de paciente FALLIDO!\n" + me.getMessage());
            return "formulario_historia_clinica.html";

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "formulario_historia_clinica.html";
        }
    }
    
    

}