package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Repository.TurnoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ariel
 */

@Service
public class TurnoService {
    
    @Autowired
    ProfesionalRepositorio profesionalRepositorio; 
    
    @Autowired
    PacienteRepositorio pacienteRepositorio;
    
    @Autowired
    TurnoRepositorio turnoRepositorio;
    
    @Transactional
    public void crearTurno(@RequestParam Date fecha, @RequestParam String idProfesional, @RequestParam String idPaciente) throws MiExcepcion {
        
        validar(fecha, idProfesional, idPaciente);
        
        Turno turno = new Turno();
        turno.setFecha(fecha);
        turno.setProfesional(profesionalRepositorio.getById(idProfesional));
        turno.setPaciente(pacienteRepositorio.getById(idPaciente));
        
        turnoRepositorio.save(turno);
    }
    
    @Transactional
    public void modificarTurno(@RequestParam String idTurno, @RequestParam Date fecha, @RequestParam String idProfesional, @RequestParam String idPaciente) {
        
        Optional<Turno> respuesta = turnoRepositorio.findById(idTurno);

        if (respuesta.isPresent()) {
            Turno turno = new Turno();
            turno.setFecha(fecha);
            turno.setProfesional(profesionalRepositorio.getById(idProfesional));
            turno.setPaciente(pacienteRepositorio.getById(idPaciente));
            
            turnoRepositorio.save(turno);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Turno> listarTurnos() {
        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.findAll();
        
        return turnos;
    }
    
    @Transactional
    public void eliminarTurno(@RequestParam String idTurno) {
        
        Optional<Turno> respuesta = turnoRepositorio.findById(idTurno);
        
        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();
            turnoRepositorio.delete(turno);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Turno> buscarTurnoPorFecha(@RequestParam Date fecha) {
        
        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorFecha(fecha);
        
        return turnos;
    }
    
    @Transactional(readOnly = true)
    public List<Turno> buscarTurnoPorApellidoProfesional(@RequestParam String apellidoProfesional) {
        
        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorApellidoProfesional(apellidoProfesional);
        
        return turnos;
    }
    
    @Transactional(readOnly = true)
    public List<Turno> buscarTurnoPorNombreProfesional(@RequestParam String nombreProfesional) {
        
        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorNombreProfesional(nombreProfesional);
        
        return turnos;
    }
    
    @Transactional(readOnly = true)
    public List<Turno> buscarTurnoPorApellidoPaciente(@RequestParam String apellidoPaciente) {
        
        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorApellidoPaciente(apellidoPaciente);
        
        return turnos;
    }
    
    @Transactional(readOnly = true)
    public List<Turno> buscarTurnoPorNombrePaciente(@RequestParam String nombrePaciente) {
        
        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorNombrePaciente(nombrePaciente);
        
        return turnos;
    }

    private void validar(Date fecha, String idProfesional, String idPaciente) throws MiExcepcion {
        
        if (fecha == null) {
                    throw new MiExcepcion("Debe ingreasar la fecha");
                }
        
        if (idProfesional == null || idProfesional.isEmpty()) {
                    throw new MiExcepcion("El profesional no puede ser nulo o vacío");
                }
        
        if (idPaciente == null || idPaciente.isEmpty()) {
                    throw new MiExcepcion("El paciente no puede ser nulo o vacío");
                } 
    }
}
