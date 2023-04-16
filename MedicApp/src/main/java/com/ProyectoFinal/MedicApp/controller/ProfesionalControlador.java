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
import org.springframework.ui.ModelMap;
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
    ProfesionalService profesionalServicio;

    @PostMapping("/buscarespec")
    public String buscarespec(@RequestParam("buscaespec") String especialidad, ModelMap model) {
        
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidad(especialidad);
        model.addAttribute("profesionales", profesionales);
        return "listaespecialidad.html";
    }
}
