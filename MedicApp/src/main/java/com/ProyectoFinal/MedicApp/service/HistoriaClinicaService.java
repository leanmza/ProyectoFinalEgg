/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.service;

import com.ProyectoFinal.MedicApp.entity.HistoriaClinica;
import com.ProyectoFinal.MedicApp.entity.Paciente;
import com.ProyectoFinal.MedicApp.entity.Profesional;
import com.ProyectoFinal.MedicApp.repository.HistoriaClinicaRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ProyectoFinal.MedicApp.exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.repository.ProfesionalRepositorio;

import java.util.ArrayList;
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

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Transactional
    public void crearHistoriaClinica(Paciente paciente, Date fechaConsulta, Profesional profesional, String diagnostico) throws MiExcepcion {

        validar(paciente, profesional, diagnostico);

        HistoriaClinica historiaClinica = new HistoriaClinica();

        historiaClinica.setPaciente(paciente);
        historiaClinica.setFechaConsulta(fechaConsulta);
        historiaClinica.setProfesional(profesional);
        historiaClinica.setDiagnostico(diagnostico);

        historiaClinicaRepositorio.save(historiaClinica);
    }

    public void modificarHistoriaClinica(String idHistoriaClinica, Paciente paciente, Date fechaConsulta, Profesional profesional, String diagnostico) throws MiExcepcion {

        validar(paciente, profesional, diagnostico);

        Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(idHistoriaClinica); //busco la historia clinica

        if (respuesta.isPresent()) {

            HistoriaClinica historiaClinica = new HistoriaClinica();

            historiaClinica.setPaciente(paciente);
            historiaClinica.setProfesional(profesional);
            historiaClinica.setFechaConsulta(fechaConsulta);
            historiaClinica.setDiagnostico(diagnostico);

            historiaClinicaRepositorio.save(historiaClinica);
        }
    }

    @Transactional(readOnly = true)
    public List<HistoriaClinica> listar(String dniPaciente) {
        
        System.out.println(dniPaciente);
        Paciente paciente = pacienteRepositorio.buscarPorDni(dniPaciente);

        String idPaciente = paciente.getId();
        System.out.println("idPaciente");
        List<HistoriaClinica> historiasClinicas; //= new ArrayList();

        historiasClinicas = historiaClinicaRepositorio.buscarPorPaciente(idPaciente);
        
        return historiasClinicas;
    }

    public void validar(Paciente paciente, Profesional profesional, String diagnostico) throws MiExcepcion {

        try {
            if (paciente == null) {
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
