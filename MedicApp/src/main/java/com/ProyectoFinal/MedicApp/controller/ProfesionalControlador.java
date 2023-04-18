/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/buscarespec")
    public String buscarespec(@RequestParam("buscaespec") String especialidad, ModelMap model) {
        
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidad(especialidad);
        model.addAttribute("profesionales", profesionales);
        return "listaespecialidad.html";
    }

    @GetMapping("/registrarProfesional")
    public String registrarProfesional() {
        return "formulario_profesional.html"; //ver nombre de archivo
    }

//    @PostMapping("/regitroProfesional")
//    public String registroProfesional(@RequestParam String nombre, @RequestParam String apellido,
//                                      @RequestParam String email, @RequestParam String telefono, @RequestParam MultipartFile archivo,
//            /*MultipartFile archivo,*/ @RequestParam String password, @RequestParam String password2,
//                                      @RequestParam String especialidad, @RequestParam String modalidad,
//                                      @RequestParam String ubicacion, @RequestParam Date horarioInicio,
//                                      @RequestParam Date horarioFin, @RequestParam Date dias, @RequestParam List<ObraSocial> obraSocial,
//                                      @RequestParam List<Turno> turnos) {
//        try {
//
//            profesionalService.crearProfesional(nombre, apellido, email, telefono, archivo,
//                    password, password2, especialidad, modalidad, ubicacion, horarioInicio,
//                    horarioFin, dias, obraSocial, turnos);
//            System.out.println("Ingreso exitoso");
//            return "index.html";
//        } catch (MiExcepcion me) {
//            System.out.println("Ingreso fallido\n" + me.getMessage());
//            return "formulario_profesional.html";
//        }
//    }
}
