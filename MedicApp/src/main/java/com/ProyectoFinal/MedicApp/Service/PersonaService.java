package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Persona;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class PersonaService {

    @Autowired
    private PersonaRepositorio personaRepositorio;

    @Transactional

    public void crearPersona(String nombre, String apellido, String email, String telefono, String password) throws MiExcepcion {



        validarPersona(nombre, apellido,email, telefono, password);
        
        Persona persona = new Persona();
        persona.setApellido(apellido);
        persona.setEmail(email);
        persona.setTelefono(telefono);
        persona.setPassword(password);

        personaRepositorio.save(persona);

    }

    public void validarPersona(String nombre, String apellido, String email, String telefono, String password) throws MiExcepcion {
        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new MiExcepcion("El nombre de la persona no puede ser nulo o vacío");
            }

            if (apellido == null || apellido.isEmpty()) {
                throw new MiExcepcion("El apellido de la persona no puede ser nulo o vacío");
            }

            if (email == null || email.isEmpty()) {
                throw new MiExcepcion("El correo electrónico de la persona no puede ser nulo o vacío");
            }

            if (telefono == null || telefono.isEmpty()) {
                throw new MiExcepcion("El teléfono de la persona no puede ser nulo o vacío");
            }

            if (password == null || password.isEmpty()) {
                throw new MiExcepcion("La contraseña de la persona no puede ser nula o vacía");
            }

        } catch (MiExcepcion ex) {
            throw ex;
        }
    }
}
