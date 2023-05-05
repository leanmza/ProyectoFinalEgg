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

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha") //poco uso salvo para un administrador que quiera ver todos los turno 
    public List<Turno> buscarPorFecha(@Param("fecha") Date fecha); //de todos los profesionales para una fecha determinada, para algo especifico hay que incluir el id del o Profesional

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha AND t.profesional = :idProfesional") 
    public List<Turno> buscarPorFechaYProfesional(@Param("fecha") Date fecha,  @Param ("idProfesional")String idProfesional); 
    
    @Query("SELECT t FROM Turno t WHERE t.paciente = :idPaciente")  //A este lo usamos para Mis Turnos
    public List<Turno> buscarPorPaciente(@Param("idPaciente") String idPaciente);

    @Query("SELECT t FROM Turno t WHERE t.profesional = :idProfesional")  //A este lo usamos para Agenda
    public List<Turno> buscarPorProfesional(@Param("idProfesional") String idProfesional);

//    @Query("SELECT t FROM Turno t JOIN t.profesional tp WHERE tp.apellido = :apellidoProfesional")
//    public List<Turno> buscarPorApellidoProfesional(@Param("apellidoProfesional") String apellidoProfesional); 
//    
//    @Query("SELECT t FROM Turno t JOIN t.profesional tp WHERE tp.nombre = :nombreProfesional")
//    public List<Turno> buscarPorNombreProfesional(@Param("nombreProfesional") String nombreProfesional);
//    
//    @Query("SELECT t FROM Turno t JOIN t.paciente tp WHERE tp.apellido = :apellidoPaciente")
//    public List<Turno> buscarPorApellidoPaciente(@Param("apellidoPaciente") String apellidoPaciente);
//    
//    @Query("SELECT t FROM Turno t JOIN t.paciente tp WHERE tp.nombre = :nombrePaciente")
//    public List<Turno> buscarPorNombrePaciente(@Param("nombrePaciente") String nombrePaciente);
}
