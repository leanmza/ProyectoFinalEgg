package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Repository.TurnoRepositorio;
import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ariel
 */
@Service
public class TurnoService {

    @Autowired
    TurnoRepositorio turnoRepositorio;

    @Transactional
    public void crearTurno(Profesional profesional, Paciente paciente, String fecha, String horario,
            String motivo) throws MiExcepcion {

        validar(profesional, paciente, fecha, horario, motivo);

        Turno turno = new Turno();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy"); // Da formato a la fecha / Si lo saco se rompe //REVISAR ESTO SI EL MES 10 U 11
        LocalDate fechaConsulta = LocalDate.parse(fecha, formatter); //Convierte String en LocalDate
        turno.setFecha(fechaConsulta);

        LocalTime hora = LocalTime.parse(horario); //Convirte String en LocalTime
        turno.setHora(hora);

        turno.setProfesional(profesional);
        turno.setPaciente(paciente);
        turno.setMotivo(motivo);
        turnoRepositorio.save(turno);
    }

//    @Transactional
//    public void modificarTurno(String idTurno, String fecha, LocalTime hora, String idProfesional,
//            String idPaciente, String motivo) {
//
//        Optional<Turno> respuesta = turnoRepositorio.findById(idTurno);
//
//        if (respuesta.isPresent()) {
//            Turno turno = new Turno();
//            turno.setFecha(fecha);
//            turno.setHora(hora);
//            turno.setProfesional(profesionalRepositorio.getById(idProfesional));
//            turno.setPaciente(pacienteRepositorio.getById(idPaciente));
//            turno.setMotivo(motivo);
//            turnoRepositorio.save(turno);
//        }
//    }
    @Transactional(readOnly = true) //poco uso, solo para un administrador 
    public List<Turno> listarTurnos() {
        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.findAll();
        return turnos;
    }

    @Transactional
    public void eliminarTurno(String idTurno) {

        Optional<Turno> respuesta = turnoRepositorio.findById(idTurno);

        System.out.println("RESPUESTA " + respuesta);
        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();
            System.out.println("TURNO " + turno);
            turnoRepositorio.delete(turno);
        }
    }

    @Transactional(readOnly = true) //poco uso, solo para un administrador 
    public List<Turno> buscarTurnoPorFecha(Date fecha) {

        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorFecha(fecha);

        return turnos;
    }

    @Transactional(readOnly = true)
    public List<Turno> buscarPorFechaYProfesional(Date fecha, String idProfesional) throws Exception {

        try {
            List<Turno> turnos = new ArrayList<>();
            turnos = turnoRepositorio.buscarPorFechaYProfesional(fecha, idProfesional);
            return turnos;

        } catch (Exception e) {
            throw new Exception("No se encuentra turnos para esa fecha y profesional");
        }

    }

    @Transactional(readOnly = true)
    public List<Turno> buscarPorPaciente(String idPaciente) {

        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorPaciente(idPaciente);

        return turnos;
    }

    @Transactional(readOnly = true)
    public List<Turno> buscarPorNombreProfesional(String idProfesional) {

        List<Turno> turnos = new ArrayList<>();
        turnos = turnoRepositorio.buscarPorProfesional(idProfesional);

        return turnos;
    }

    private void validar(Profesional profesional, Paciente paciente, String fecha, String hora,
            String motivo) throws MiExcepcion {

        if (profesional == null) {
            throw new MiExcepcion("El profesional no puede ser nulo");
        }
        if (paciente == null) {
            throw new MiExcepcion("El paciente no puede ser nulo");
        }

        if (fecha == null) {
            throw new MiExcepcion("El día no puede ser nulo o vacío");
        }

        if (hora == null) {
            throw new MiExcepcion("El horario no puede ser nulo o vacío");
        }

        if (motivo == null || motivo.isEmpty()) {
            throw new MiExcepcion("El paciente no puede ser nulo o vacío");
        }

    }
}
