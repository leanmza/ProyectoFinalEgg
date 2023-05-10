package com.ProyectoFinal.MedicApp.Repository;

import com.ProyectoFinal.MedicApp.Entity.Profesional;
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

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha") //poco uso salvo para un administrador que quiera ver todos los turno 
    public List<Turno> buscarPorFecha(@Param("fecha") Date fecha); //de todos los profesionales para una fecha determinada, para algo especifico hay que incluir el id del o Profesional

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha AND t.profesional = :idProfesional") 
    public List<Turno> buscarPorFechaYProfesional(@Param("fecha") Date fecha,  @Param ("idProfesional")String idProfesional); 
    
    @Query("SELECT t  FROM Turno t JOIN t.paciente p WHERE p.id = :idPaciente AND t.fecha >= CURRENT_DATE" )  //A este lo usamos para Mis Turnos
    public List<Turno> buscarPorPaciente(@Param("idPaciente") String idPaciente);

    @Query("SELECT t FROM Turno t JOIN t.profesional pr WHERE pr.id = :idProfesional AND t.fecha >= CURRENT_DATE" )  //A este lo usamos para Agenda
    public List<Turno> buscarPorProfesional(@Param("idProfesional") String idProfesional);

    @Query("SELECT DISTINCT t.profesional FROM Turno t JOIN t.paciente p WHERE p.id = :idPaciente")
    public List<Profesional> buscarProfesionalPorPaciente(@Param("idPaciente") String idPaciente);

}
