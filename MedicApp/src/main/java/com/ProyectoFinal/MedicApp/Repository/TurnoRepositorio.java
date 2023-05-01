package com.ProyectoFinal.MedicApp.Repository;

import com.ProyectoFinal.MedicApp.Entity.Turno;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ariel
 */

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {
    
    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha")
    public List<Turno> buscarPorFecha(@Param("fecha") Date fecha);
    
    @Query("SELECT t FROM Turno t JOIN t.profesional tp WHERE tp.apellido = :apellidoProfesional")
    public List<Turno> buscarPorApellidoProfesional(@Param("apellidoProfesional") String apellidoProfesional); 
    
    @Query("SELECT t FROM Turno t JOIN t.profesional tp WHERE tp.nombre = :nombreProfesional")
    public List<Turno> buscarPorNombreProfesional(@Param("nombreProfesional") String nombreProfesional);
    
    @Query("SELECT t FROM Turno t JOIN t.paciente tp WHERE tp.apellido = :apellidoPaciente")
    public List<Turno> buscarPorApellidoPaciente(@Param("apellidoPaciente") String apellidoPaciente);
    
    @Query("SELECT t FROM Turno t JOIN t.paciente tp WHERE tp.nombre = :nombrePaciente")
    public List<Turno> buscarPorNombrePaciente(@Param("nombrePaciente") String nombrePaciente);
    
    @Query("SELECT t FROM Turno t Join t.paciente tp WHERE tp.dni = :dni")
    public List<Turno> buscarPorDniPaciente(@Param("dni") String dni);
}
