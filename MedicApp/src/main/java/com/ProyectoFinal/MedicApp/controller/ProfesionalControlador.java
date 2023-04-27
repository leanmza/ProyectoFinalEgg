/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Profesional;
<<<<<<< HEAD
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
>>>>>>> Developer
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

/**
 *
 * @author cmoro1
 */

@Controller
@RequestMapping("/profesional")
@PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
public class ProfesionalControlador {
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    ProfesionalService profesionalService;

    @Transactional
    @PostMapping("/buscarespec")
    public String buscarespec(@RequestParam("especialidad") String especialidad, ModelMap model) {        //buscaespec
        System.out.println(especialidad);
        List<Profesional> profesionales = profesionalService.buscarProfesionalesPorEspecialidad(especialidad);
        model.addAttribute("profesionales", profesionales);
        return "listaespecialidad.html";
    }
    
    @Transactional
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        List<Profesional> profesionales = profesionalService.listar();
        model.addAttribute("profesionales", profesionales);
        return "listar.html";
    }
    
<<<<<<< HEAD
    @Transactional
    @GetMapping("/perfil")
    public String perfil(Model modelo, HttpSession session) {
        Profesional profesional = (Profesional) session.getAttribute("profesionalSession");
        modelo.addAttribute("profesional", profesional);
        
        List<String> ubicaciones = new ArrayList<>();
        for (Ubicacion aux : Ubicacion.values()) {
            ubicaciones.add(aux.toString());
        }
        modelo.addAttribute("ubicaciones", ubicaciones);
        
        List<String> modalidades = new ArrayList<>();
        for (Modalidad aux : Modalidad.values()) {
            modalidades.add(aux.toString());
        }
        modelo.addAttribute("modalidades", modalidades);
        
        return "editar_profesional.html";
=======

 @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Profesional profesional = (Profesional) session.getAttribute("profesionalSession");
        modelo.put("profesional", profesional);
        
        return "editar_profesional.html";
    }
        
    
    @PostMapping("/perfil/{id}")
    public String modificarPerfil(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String password,
            @RequestParam String password2,  @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios,/*,
           @RequestParam("obrasSociales[]") List<String> obrasSociales, @RequestParam("dias[]") List<String> dias,
            @RequestParam("horaInicio") LocalTime horaInicio, @RequestParam ("horaFin") LocalTime horaFin
            , @RequestParam(required = false) List<Turno>turnos*/  HttpSession session, ModelMap modelo ) {
        
        try {
    
            profesionalService.modificarProfesional(modalidad, nombre, apellido, correo, telefono, password, password2,
                    especialidad, ubicacion, modalidad, honorarios);
       
            session.setAttribute("profesionalSession", profesionalService.getOne(id));
            return "redirect:/inicio";
            
        } catch (MiExcepcion me) {
            System.out.println("Ingreso de profesional FALLIDO!\n" + me.getMessage());
            
            return "editar_profesional.html";
            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "editar_profesional.html";
        }
>>>>>>> Developer
    }
    
    @Transactional
    @PostMapping("perfil/{id}")
    public String modificarPerfil(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String password,
            @RequestParam String password2,  @RequestParam String especialidad,
            @RequestParam String ubicacion, @RequestParam String modalidad, @RequestParam Double honorarios,
            /*@RequestParam("obrasSociales[]") List<String> obrasSociales, @RequestParam("dias[]") List<String> dias,*/
            @RequestParam("horaInicio") LocalTime horaInicio, @RequestParam ("horaFin") LocalTime horaFin
            /*, @RequestParam(required = false) List<Turno>turnos*/ ) {
        
        try {
            profesionalService.modificarProfesional(id, nombre, apellido, correo, telefono, password, password2, 
                    especialidad, ubicacion, modalidad, honorarios, LocalTime.MIN, LocalTime.MIN);
            
            return "redirect:/inicio";
            
        } catch (MiExcepcion e) {
            System.out.println("Error al actualizar Profesional");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "editar_profesional.html";
        }
       
    }
    
}
