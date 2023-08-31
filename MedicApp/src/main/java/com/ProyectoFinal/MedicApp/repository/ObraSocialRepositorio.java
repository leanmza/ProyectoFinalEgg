/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.repository;

import com.ProyectoFinal.MedicApp.entity.ObraSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lean
 */
@Repository
public interface ObraSocialRepositorio extends JpaRepository <ObraSocial, String> {
     
    @Query("SELECT os FROM ObraSocial os WHERE os.nombre = :nombre")
    public ObraSocial buscarPorNombre(@Param("nombre") String nombre);
    
    
    
}
