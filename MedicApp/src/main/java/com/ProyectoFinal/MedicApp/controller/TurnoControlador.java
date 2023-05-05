/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;


import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    
   @GetMapping("/formularioTurno")
   public String turno(@RequestParam String idProfesional,Model model){

    Optional<Profesional> profesional = profesionalRepositorio.findById(idProfesional); //SEGUN EL CHAT ESTO DEBERÍA ANDAR PERO ME TIRÓ UN 404 ASIQUE ESTA CERCA DE ANDAR
    model.addAttribute("profesional", profesional);
       System.out.println("profesional" + profesional);
       
   return "formulario_turno.html";
    }
   
   
   @Transactional
    @PostMapping("/registroTurno")
    public String registroTurno(@RequestParam String especialidad, @RequestParam String nombre, @RequestParam String apellido ) throws MiExcepcion{
       
////        try {
////            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
////            Date fechaNacimiento = formato.parse(nacimiento);
////            
////    
////
////            System.out.println("Turno exitoso");
////            return "redirect:/inicio";
//
//        } catch (MiExcepcion me) {
//            System.out.println("Registro de turno FALLIDO!\n" + me.getMessage());
//
//            return "formulario_paciente.html";
//
//        } catch (ParseException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
            return "inicio.html";
        }
    }
   
   
   

