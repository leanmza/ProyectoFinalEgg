/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.HistoriaClinica;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Repository.HistoriaClinicaRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
//import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Lean
 */
@Service

public class HistoriaClinicaService {

    public class ObraSocialService {

        @Autowired
        private HistoriaClinicaRepositorio historiaClinicaRepositorio;

        @Autowired 
                private PacienteRepositorio pacienteRepositorio;
        
        @Autowired
        private ProfesionalRepositorio profesionalRepositorio;

        @Transactional
        public void crearHistoriaClinica(String idPaciente, String idProfesional, String diagnostico) throws MiExcepcion {

            validar(idPaciente, idProfesional, diagnostico);

            HistoriaClinica historiaClinica = new HistoriaClinica();

            Optional<Paciente> respuestaPaciente = pacienteRepositorio.findById(idPaciente);

            if (respuestaPaciente.isPresent()) {
                Paciente paciente = respuestaPaciente.get();
                historiaClinica.setPaciente(paciente);
            }
            Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);

            if (respuestaProfesional.isPresent()) {
                Profesional profesional = respuestaProfesional.get();
                historiaClinica.setProfesional(profesional);
            }

            Date fechaConsulta = new Date();

            historiaClinica.setFechaConsulta(fechaConsulta);
            historiaClinica.setDiagnostico(diagnostico);

            historiaClinicaRepositorio.save(historiaClinica);
        }
        
                @Transactional
       
                public void modificarHistoriaClinica(String idHistoriaClinica, String idPaciente, String idProfesional, String diagnostico) throws MiExcepcion {

            validar(idPaciente, idProfesional, diagnostico);

            Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(idHistoriaClinica); //busco la historia clinica
            
            if(respuesta.isPresent()){
            
            HistoriaClinica historiaClinica = new HistoriaClinica();

            Optional<Paciente> respuestaPaciente = pacienteRepositorio.findById(idPaciente); //busco el paciente

            if (respuestaPaciente.isPresent()) {
                Paciente paciente = respuestaPaciente.get();
                historiaClinica.setPaciente(paciente);
            }
            Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional); // busco el profesional

            if (respuestaProfesional.isPresent()) {
                Profesional profesional = respuestaProfesional.get();
                historiaClinica.setProfesional(profesional);
            }

            Date fechaConsulta = new Date();

            historiaClinica.setFechaConsulta(fechaConsulta);
            historiaClinica.setDiagnostico(diagnostico);

            historiaClinicaRepositorio.save(historiaClinica);
        }
                }
                
                
    @Transactional(readOnly = true)
    public List<HistoriaClinica> listar(String idPaciente) {

        List<HistoriaClinica> historiasClinicas = new ArrayList();

        historiasClinicas = historiaClinicaRepositorio.buscarPorPaciente(idPaciente);

        return historiasClinicas;
    }
        
        public void validar(String idPaciente, String idProfesional, String diagnostico) throws MiExcepcion {

            try {
                if (idPaciente == null || idPaciente.isEmpty()) {
                    throw new MiExcepcion("El paciente no puede ser nulo o vacío");
                }

                if (idProfesional == null || idProfesional.isEmpty()) {
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

}
