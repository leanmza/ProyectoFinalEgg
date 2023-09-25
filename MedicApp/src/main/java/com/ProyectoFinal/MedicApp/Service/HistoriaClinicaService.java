/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.HistoriaClinica;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Repository.HistoriaClinicaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Lean
 */
@Service
public class HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Transactional
    public void crearHistoriaClinica(String dni, String fechaConsulta, Profesional profesional, String diagnostico, String tratamiento) throws MiExcepcion {

        Paciente paciente = pacienteRepositorio.buscarPorDni(dni);

        validar(paciente, fechaConsulta, profesional, diagnostico);

        HistoriaClinica historiaClinica = new HistoriaClinica();

        historiaClinica.setPaciente(paciente);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaDeConsulta = LocalDate.parse(fechaConsulta, formatter);
        historiaClinica.setFechaConsulta(fechaDeConsulta);

        historiaClinica.setProfesional(profesional);
        historiaClinica.setDiagnostico(diagnostico);

        historiaClinicaRepositorio.save(historiaClinica);
    }

    public void modificarHistoriaClinica(String idHistoriaClinica, String dni, String fechaConsulta, Profesional profesional, String diagnostico) throws MiExcepcion {

        Paciente paciente = pacienteRepositorio.buscarPorDni(dni);

        validar(paciente, fechaConsulta, profesional, diagnostico);

        Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(idHistoriaClinica); //busco la historia clinica

        if (respuesta.isPresent()) {

            HistoriaClinica historiaClinica = new HistoriaClinica();

            historiaClinica.setPaciente(paciente);
            historiaClinica.setProfesional(profesional);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaDeConsulta = LocalDate.parse(fechaConsulta, formatter);
            historiaClinica.setFechaConsulta(fechaDeConsulta);

            historiaClinica.setDiagnostico(diagnostico);

            historiaClinicaRepositorio.save(historiaClinica);
        }
    }

    @Transactional(readOnly = true)
    public List<HistoriaClinica> listar(String dniPaciente) {

        Paciente paciente = pacienteRepositorio.buscarPorDni(dniPaciente);

        String idPaciente = paciente.getId();

        List<HistoriaClinica> historiasClinicas; //= new ArrayList();

        historiasClinicas = historiaClinicaRepositorio.buscarPorPaciente(idPaciente);

        return historiasClinicas;
    }

    public void validar(Paciente paciente, String fechaConsulta, Profesional profesional, String diagnostico) throws MiExcepcion {

        try {
            if (paciente == null) {
                throw new MiExcepcion("El paciente no puede ser nulo o vacío");
            }

            if (fechaConsulta == null || fechaConsulta.isEmpty()) {
                throw new MiExcepcion("El paciente no puede ser nulo o vacío");
            }

            if (profesional == null) {
                throw new MiExcepcion("El profesional no puede ser nulo o vacío");
            }

            if (diagnostico == null || diagnostico.isEmpty()) {
                throw new MiExcepcion("El dignostico no puede ser nulo o vacío");
            }

        } catch (MiExcepcion ex) {
            throw ex;
        }

    }
}
