/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Repository;

import com.ProyectoFinal.MedicApp.Entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lean
 */

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {
    
    @Query("SELECT p FROM Persona p WHERE p.email = :email")
    public Persona findByEmail(@Param("email") String email);
}
