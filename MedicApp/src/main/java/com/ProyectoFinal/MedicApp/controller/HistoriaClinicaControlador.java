///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.ProyectoFinal.MedicApp.controller;
//
//import com.ProyectoFinal.MedicApp.Entity.HistoriaClinica;
//import com.ProyectoFinal.MedicApp.Repository.HistoriaClinicaRepositorio;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// *
// * @author Lean
// */
//    @Controller
//@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_PROFESIONAL')")
//@RequestMapping("/historiaClinica")
//public class HistoriaClinicaControlador {
//     
//
//
//    @Autowired
//    HistoriaClinicaRepositorio historiaClinicaRepositorio;
//    
////    @Autowired
////    ProfesionalService profesionalService;
//
//    @Transactional
//    @GetMapping("/listar")
//    public String listar(ModelMap model) {
//        List<HistoriaClinica> historiasClinicas = historiaClinicaRepositorio.buscarPorPaciente(idPaciente);
//        model.addAttribute("historiasClinicas", historiasClinicas);
//        return "listar.html";
//    }
//}
//    
//
//    
//    
//
