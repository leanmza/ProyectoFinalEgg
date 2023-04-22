/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cmoro1
 */

@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Transactional
    public void crearPaciente(String nombre, String apellido, String email, String telefono, String password,
            String password2, String direccion, Date fechaNacimiento, String sexo) throws MiExcepcion {

        validar(nombre, apellido, email, telefono, password, password2, direccion, fechaNacimiento, sexo);

        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        paciente.setPassword(password);
        paciente.setDireccion(direccion);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setSexo(sexo);
        paciente.setRol(Rol.PACIENTE);
        paciente.setActivo(true);
        pacienteRepositorio.save(paciente);
    }

    public Paciente getOne(String id) {
        return pacienteRepositorio.getOne(id);
    }
    
    @Transactional(readOnly = true)
    public List<Paciente>listar() {
 
        List<Paciente> pacientes = new ArrayList();

        pacientes = pacienteRepositorio.findAll();

        return pacientes;
    }

    public void validar(String nombre, String apellido, String email, String telefono, String password,
            String password2, String direccion, Date fechaNacimiento, String sexo) throws MiExcepcion {

        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new MiExcepcion("El nombre no puede ser nulo o vacío");
            }

            if (apellido == null || apellido.isEmpty()) {
                throw new MiExcepcion("El apellido no puede ser nulo o vacío");
            }

            if (email == null || email.isEmpty()) {
                throw new MiExcepcion("El correo electrónico no puede ser nulo o vacío");
            }

            if (telefono == null || telefono.isEmpty()) {
                throw new MiExcepcion("El teléfono no puede ser nulo o vacío");
            }

            if (password == null || password.isEmpty()) {
                throw new MiExcepcion("La contraseña no puede ser nula o vacía");
            }

            if (password2 == null || password2.isEmpty()) {
                throw new MiExcepcion("La segunda contraseña no puede ser nula o vacía");
            }

            if (password != password2) {
                throw new MiExcepcion("Las contraseñas no coinciden");
            }

            if (direccion == null || direccion.isEmpty()) {
                throw new MiExcepcion("La dirección no puede ser nula o vacía");
            }

            if (fechaNacimiento == null) {
                throw new MiExcepcion("La fecha no puede ser nula");
            }
            
            if (sexo == null || sexo.isEmpty()) {
                throw new MiExcepcion("El sexo no puede ser nulo o vacío");
            }

        } catch (MiExcepcion ex) {
            throw ex;
        }
    }
}
