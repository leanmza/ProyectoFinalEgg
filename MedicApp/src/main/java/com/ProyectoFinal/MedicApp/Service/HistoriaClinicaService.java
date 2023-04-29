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
        public void crearHistoriaClinica(String dni, Date fechaConsulta, String idProfesional, String diagnostico) throws MiExcepcion {

            crearHistoriaClinica(dni, fechaConsulta, idProfesional, diagnostico);
            
            validar(dni, idProfesional, diagnostico);

            HistoriaClinica historiaClinica = new HistoriaClinica();

            Paciente paciente = pacienteRepositorio.buscarPorDni(dni); // Me tiraba error con el optional
                 
                historiaClinica.setPaciente(paciente);
     
            
            Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional);

            if (respuestaProfesional.isPresent()) {
                Profesional profesional = respuestaProfesional.get();
                historiaClinica.setProfesional(profesional);
            }

            historiaClinica.setFechaConsulta(fechaConsulta);
            historiaClinica.setDiagnostico(diagnostico);

            historiaClinicaRepositorio.save(historiaClinica);
        }

        @Transactional

        public void modificarHistoriaClinica(String idHistoriaClinica, String dni, Date fechaConsulta, String idProfesional, String diagnostico) throws MiExcepcion {

            validar(dni, idProfesional, diagnostico);

            Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(idHistoriaClinica); //busco la historia clinica

            if (respuesta.isPresent()) {

                HistoriaClinica historiaClinica = new HistoriaClinica();

               Paciente paciente = pacienteRepositorio.buscarPorDni(dni); // Me tiraba error con el optional

           
                    historiaClinica.setPaciente(paciente);
         
                Optional<Profesional> respuestaProfesional = profesionalRepositorio.findById(idProfesional); // busco el profesional

                if (respuestaProfesional.isPresent()) {
                    Profesional profesional = respuestaProfesional.get();
                    historiaClinica.setProfesional(profesional);
                }



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

        @Transactional(readOnly = true)
        public List<HistoriaClinica> listar() {

            List<HistoriaClinica> historiasClinicas = new ArrayList();

            historiasClinicas = historiaClinicaRepositorio.findAll();

            return historiasClinicas;
        }

        public void validar(String dni, String idProfesional, String diagnostico) throws MiExcepcion {

            try {
                if (dni == null || dni.isEmpty()) {
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

