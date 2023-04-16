/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Lean
 */
public class ProfesionalServicio {
    
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;
            
    public List<Profesional> buscarProfesionalesPorEspecialidad(String especialidad){
        List<Profesional> profesionales = new ArrayList();
        profesionales = (List<Profesional>) profesionalRepositorio.buscarPorEspecialidad(especialidad);
        return profesionales;
    }
}
