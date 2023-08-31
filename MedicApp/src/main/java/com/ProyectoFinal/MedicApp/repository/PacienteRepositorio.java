/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.repository;

import com.ProyectoFinal.MedicApp.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cmoro1
 */

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {

    @Query("SELECT pa FROM Paciente pa WHERE pa.email = :email")
    public Paciente buscarPorEmail(@Param("email") String email);

    @Query("SELECT pa FROM Paciente pa WHERE pa.dni = :dni")
    public Paciente buscarPorDni(@Param("dni") String dni);
}
