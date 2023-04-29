/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author cmoro1
 */

@Controller
@RequestMapping("/pro")
public class ProfesionalControlador {
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    ProfesionalService profesionalService;

    @Transactional
    @PostMapping("/buscarespec")
    public String buscarespec(@RequestParam("especialidad") String especialidad, ModelMap model) {
        System.out.println(especialidad);
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidad(especialidad);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("espec", especialidad);
        return "listaespecialidad.html";
    }

    @Transactional
    @PostMapping("/buscarespechonorario")
    public String buscarespechonorario(@RequestParam("especialidad") String especialidad, ModelMap model) {
        System.out.println(especialidad);
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidadOrdenadoHonorario(especialidad);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("espec", especialidad);
        return "listaespecialidad.html";
    }

    @Transactional
    @PostMapping("/buscarespeccalificacion")
    public String buscarespeccalificacion(@RequestParam("especialidad") String especialidad, ModelMap model) {
        System.out.println(especialidad);
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidadOrdenadoCalificacion(especialidad);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("espec", especialidad);
        return "listaespecialidad.html";
    }
    
    @Transactional
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        List<Profesional> profesionales = profesionalService.listar();
        model.addAttribute("profesionales", profesionales);
        return "listar.html";
    }
    
    @GetMapping("/form_pro")
    public String form_pro(ModelMap model) {
        return "formulario_profesional.html";
    }
}
