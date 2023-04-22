/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/pac")
public class PacienteControlador {
    
    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Autowired
    PacienteService pacienteService;

    @GetMapping("/form_pac")
    public String form_pac(ModelMap model) {
        return "formulario_paciente.html";
    }
    
    @PostMapping("/registroPaciente")
    public String registroPaciente(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String correo, @RequestParam String telefono, @RequestParam String nacimiento,
            @RequestParam String password, @RequestParam String password2) {
       
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //yyyy-MM-dd
            Date fechaNacimiento = formato.parse(nacimiento);
            
            pacienteService.crearPaciente(nombre, apellido, nombre, telefono, password, password2, telefono, fechaNacimiento, correo);
            System.out.println("Ingreso de paciente exitoso");
            return "redirect:/inicio";
            
        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            
            return "formulario_paciente.html";
            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "formulario_paciente.html";
        }
    }
}
