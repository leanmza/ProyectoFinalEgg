/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Repository;

import com.ProyectoFinal.MedicApp.Entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cmoro1
 */
public interface TurnoRepositorio extends JpaRepository<Turno, String>{
    
}
