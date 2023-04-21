package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Repository.TurnoRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    //PROFESIONAL: nombre, apellido
    //PACIENTE: nombre, apellido, dni, email, telefono
    //FECHA: fecha
    public void crearTurno(@RequestParam Date fecha, @RequestParam String idProfesional, @RequestParam String idPaciente) {
        
        Turno turno = new Turno();
        turno.setFecha(fecha);
        turno.setProfesional(profesionalRepositorio.getById(idProfesional));
        turno.setPaciente(pacienteRepositorio.getById(idPaciente));
        
        turnoRepositorio.save(turno);
    }
    
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
    
}
