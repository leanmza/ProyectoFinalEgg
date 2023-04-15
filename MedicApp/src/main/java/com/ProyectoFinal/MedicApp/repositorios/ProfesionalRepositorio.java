/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.repositorios;

import com.ProyectoFinal.MedicApp.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cmoro1
 */

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String>{

    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    public Profesional buscarPorEspecialidad(@Param("especialidad")String especialidad);
}
