/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lean
 */
@Service
public class ProfesionalService {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Transactional
<<<<<<< HEAD
    public void crearProfesional(String nombre, String apellido, String email, String telefono, String password, String password2, String especialidad, String modalidad, String ubicacion, Date dias, List<ObraSocial> obraSocial, List<Turno> turnos, Double honorarios) throws MiExcepcion {
=======
    public void crearProfesional(String nombre, String apellido, String email, String telefono, String password,
            String password2, String especialidad, String modalidad, String ubicacion, Date horario, Date dias,
            /*List<ObrasSociales> obrasSociales, List<Turno>turnos,*/
            Double honorarios) throws MiExcepcion {
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69

        validar(nombre, apellido, email, telefono, password, password2, especialidad, modalidad, ubicacion, dias, honorarios);

        Profesional profesional = new Profesional();

        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setEmail(email);
        profesional.setTelefono(telefono);
        profesional.setPassword(password);
        profesional.setRol(Rol.PROFESIONAL);
        profesional.setActivo(true);
        profesional.setEspecialidad(especialidad);
<<<<<<< HEAD
        profesional.setModalidad(Modalidad.valueOf(modalidad));
        profesional.setUbicacion(Ubicacion.valueOf(ubicacion));
=======

        //Falta ObrasSociales y Tunos, hay que crear las entidades
        profesional.setModalidad(Modalidad.valueOf(modalidad.toUpperCase()));
        profesional.setUbicacion(Ubicacion.valueOf(ubicacion.toUpperCase()));
        profesional.setHorario(horario);
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69
        profesional.setDias(dias);
        profesional.setObrasSociales(obraSocial);
        profesional.setTurnos(turnos);
        profesional.setCantVisitas(0);
        profesional.setPuntaje(0);
        profesionalRepositorio.save(profesional);

    }
<<<<<<< HEAD

    @Transactional
    public void modificarProfesional(String idProfesional, String nombre, String apellido, String email, String telefono, String password, String password2, String especialidad, String modalidad, String ubicacion, Date dias, List<ObraSocial> obraSocial, List<Turno> turnos, Double honorarios) throws MiExcepcion {

        validar(nombre, apellido, email, telefono, password, password2, especialidad, modalidad, ubicacion, dias, honorarios);
=======
    
    @Transactional
   public void modificarProfesional(String idProfesional, String nombre, String apellido, String email, String telefono, String password,
            String password2, String especialidad, String modalidad, String ubicacion, Date horario, Date dias, 
            /*List<ObrasSociales> obrasSociales, List<Turno>turnos,*/
            Double honorarios) throws MiExcepcion {

        validar(nombre, apellido, email, telefono, password, password2, especialidad, modalidad,
                ubicacion, horario, dias, honorarios);
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69
        Optional<Profesional> respuesta = profesionalRepositorio.findById(idProfesional);

        if (respuesta.isPresent()) {
            Profesional profesional = new Profesional();

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setEmail(email);
            profesional.setTelefono(telefono);
            profesional.setPassword(password);
            profesional.setRol(Rol.PROFESIONAL);
            profesional.setActivo(true);
            profesional.setEspecialidad(especialidad);
            profesional.setModalidad(Modalidad.valueOf(modalidad));
            profesional.setUbicacion(Ubicacion.valueOf(ubicacion));

            profesional.setDias(dias);
            profesional.setObrasSociales(obraSocial);
            profesional.setTurnos(turnos);
            profesional.setHonorario(honorarios);
            profesional.setCantVisitas(0);
            profesional.setPuntaje(0);
            profesionalRepositorio.save(profesional);
        }

    }

    public Profesional getOne(String id) {
        return profesionalRepositorio.getOne(id);
    }
<<<<<<< HEAD


    @Transactional(readOnly = true)
    public List<Profesional> listar() {

        List<Profesional> profesionales = new ArrayList();
=======
    
    
           @Transactional(readOnly = true)
    public List<Profesional>listar() {
 
        List<Profesional> profesionales; // = new ArrayList();
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69

        profesionales = profesionalRepositorio.findAll();

        return profesionales;
    }


    @Transactional(readOnly = true)
<<<<<<< HEAD
    public List<Profesional> buscarProfesionalesPorEspecialidad(String especialidad) {

        List<Profesional> especialistas = new ArrayList();

=======
    public List<Profesional>buscarProfesionalesPorEspecialidad(String especialidad){
        
        List<Profesional> especialistas; //= new ArrayList();
        
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69
        especialistas = profesionalRepositorio.buscarPorEspecialidad(especialidad);

        return especialistas;
    }
<<<<<<< HEAD


=======
    
        
    
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69
//    DESCOMENTAR CUANDO SE CREE ObrasSociales
//            @Transactional
//        public List<ObrasSociales>listarObrasSociales(String id) {
// 
//        List<ObrasSociales> obrasSociales = new ArrayList();
//
//        obrasSociales =profesionalRepositorio.findById(id);
//
//        return obrasSociales;
//    }

    //    DESCOMENTAR CUANDO SE CREE TURNOS
//            @Transactional
//        public List<Turnos>listaTurnos(String id) {
// 
//        List<Turnos> turnos = new ArrayList();
//
//        turnos =profesionalRepositorio.findById(id);
//
//        return turnos;
//    }

<<<<<<< HEAD

    public void validar(String nombre, String apellido, String email, String telefono, String password, String password2, String especialidad, String modalidad, String ubicacion, Date dias, Double honorarios) throws MiExcepcion {
=======
    public void validar(String nombre, String apellido, String email, String telefono, String password,
            String password2, String especialidad, String modalidad, String ubicacion, Date horario, 
            Date dias, Double honorarios) throws MiExcepcion {
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69

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
            if (password2 == null || password2.isEmpty()) {
                throw new MiExcepcion("Es campo no puede ser nulo o vacía");
            }

            if (password != password2) {
                throw new MiExcepcion("Las contraseñas no coinciden");

            }
            if (especialidad == null || especialidad.isEmpty()) {
                throw new MiExcepcion("La especilidad no puede ser nula o vacía");
            }
            if (modalidad == null || modalidad.isEmpty()) {
                throw new MiExcepcion("La modalidad no puede ser nula o vacía");
            }
            if (ubicacion == null || ubicacion.isEmpty()) {
                throw new MiExcepcion("La ubicacion no puede ser nula o vacía");
            }

            if (dias == null) {
                throw new MiExcepcion("Los días no pueden ser nulos");
            }
            if (honorarios == null) {
                throw new MiExcepcion("Los honorarios no pueden ser nulos");
            }


        } catch (MiExcepcion ex) {
            throw ex;
        }
    }


}


// DESCOMENTAR CUANDO SE HAGA SECURITY
//    @Override
//    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
//
//        Periodista periodista = periodistaRepositorio.buscarPorId(nombreUsuario);
//                
//        if (periodista != null) {
//
//            List<GrantedAuthority> permisos = new ArrayList();
//
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + periodista.getRol().toString());
//
//            permisos.add(p);
//
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//
//            HttpSession session = attr.getRequest().getSession(true);
//
//            session.setAttribute("usuariosession", periodista);
//
//            return new User(periodista.getNombreUsuario(), periodista.getPassword(), permisos);
//        } else {
//            return null;
//        }
//
//    }

//}