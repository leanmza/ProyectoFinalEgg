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
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cmoro1
 */

@Controller
@PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
@RequestMapping("/pac")
public class PacienteControlador {
    
    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Autowired
    PacienteService pacienteService;

    @Transactional
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Paciente paciente = (Paciente) session.getAttribute("pacienteSession");
        modelo.put("paciente", paciente);
        
        return "editar_paciente.html";
    }
    
    @Transactional
    @PostMapping("/perfil/{id}")
    public String modificarPerfil(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,  
            @RequestParam String dni, @RequestParam String correo, @RequestParam String telefono,
            @RequestParam String nacimiento, @RequestParam String password, @RequestParam String password2, 
            @RequestParam String direccion, @RequestParam String sexo, @RequestParam(required = false) MultipartFile archivo, HttpSession session, ModelMap modelo ) {
        
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //yyyy-MM-dd
            Date fechaNacimiento = formato.parse(nacimiento);
            
            System.out.println("ARCHIVO " + archivo.getContentType());
            pacienteService.modificarPaciente(id, nombre, apellido, dni, correo, telefono, password, password2, direccion, 
                    fechaNacimiento, sexo, archivo);
            session.setAttribute("pacienteSession", pacienteService.getOne(id));
            return "redirect:/inicio";
            
        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());
            
            return "editar_paciente.html";
            
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "editar_paciente.html";
        }
    }
    
    @Transactional
    @GetMapping("/baja/{id}")
    public String bajaPaciente(@PathVariable String id) {
        
        pacienteService.darDeBaja(id);
        
        return "redirect:/";
    }
}
