/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import java.util.List;
import javax.servlet.http.HttpSession;
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
 * @author Lean
 */
@Controller
@RequestMapping("/obraSocial")
public class ObraSocialController {
    
    @Autowired
    ObraSocialService obraSocialService;
    
        //FORMULARIO PARA CREAR UNA OBRA SOCIAL
    @GetMapping("/crearObraSocial")
    public String crearObraSocial(ModelMap model) {

        return "formulario_obra_social.html";
    }

    // GUARDADO DE OBRA SOCIAL NUEVA
    @Transactional
    @PostMapping("/registroObraSocial")
    public String registroObraSocial(@RequestParam("nombreObraSocial") String nombreObraSocial, HttpSession obraSocialNueva) {

        try {
            obraSocialService.crearObraSocial(nombreObraSocial);

            System.out.println("Ingreso de obra social exitoso");
            obraSocialNueva.setAttribute("nuevaObraSocial", nombreObraSocial);
            return "formulario_obra_social.html";

        } catch (MiExcepcion me) {
            System.out.println("Ingreso de obra social FALLIDO!\n" + me.getMessage());

            return "formulario_obra_social.html";

        }
    }
    
    @GetMapping("/listaObraSociales")
    public String listaObraSociales (ModelMap model){
        List<ObraSocial> obrasSociales = obraSocialService.listar();
        
        model.addAttribute("obraSocial", obrasSociales);
        return "lista_obras_sociales";
    }
}
