/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Lean
 */
@Service
public class ProfesionalService implements UserDetailsService {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Transactional
    public void crearProfesional(String nombre, String apellido, String correo, String telefono,
            String password, String password2, String especialidad, String ubicacion,
            String modalidad, Double honorarios/*, List<String> obrasSociales, List<String> dias,
            LocalTime horaInicio, LocalTime horaFin  List<ObrasSociales> obrasSociales, List<Turno>turnos,*/
            ) throws MiExcepcion {

       validar(nombre, apellido, correo, telefono, password, password2,
                    especialidad, ubicacion, modalidad, honorarios /*, obrasSociales, dias,horaInicio, horaFin*/ );

        Profesional profesional = new Profesional();

        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
//        profesional.setDni(dni);
        profesional.setEmail(correo);
        profesional.setTelefono(telefono);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        profesional.setRol(Rol.PROFESIONAL);
        profesional.setActivo(true);
        profesional.setEspecialidad(especialidad);

        profesional.setModalidad(modalidad.toString());
        profesional.setUbicacion(ubicacion.toString());
        profesional.setHonorario(honorarios);
//        profesional.setDias(dias);
//        profesional.setHoraInicio(horaInicio);
//        profesional.setHoraFin(horaFin);
        profesional.setCantVisitas(0);
        profesional.setPuntaje(0);
        profesionalRepositorio.save(profesional);

    }

    @Transactional
    public void modificarProfesional (String idProfesional, String nombre, String apellido, String correo, String telefono,
            String password, String password2, String especialidad, String ubicacion,
            String modalidad, Double honorarios,/* List<String> obrasSociales, List<String> dias,*/
            LocalTime horaInicio, LocalTime horaFin /*List<ObrasSociales> obrasSociales, List<Turno>turnos,*/
            ) throws MiExcepcion {

        validar(nombre, apellido, correo, telefono, password, password2,
                especialidad, ubicacion, modalidad, honorarios /*, obrasSociales, dias, horaInicio, horaFin*/ );
        Optional<Profesional> respuesta = profesionalRepositorio.findById(idProfesional);

        if (respuesta.isPresent()) {
            Profesional profesional = new Profesional();

         profesional.setNombre(nombre);
        profesional.setApellido(apellido);
//        profesional.setDni(dni);
        profesional.setEmail(correo);
        profesional.setTelefono(telefono);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        profesional.setRol(Rol.PROFESIONAL);
        profesional.setActivo(true);
        profesional.setEspecialidad(especialidad);
        //Falta ObrasSociales y Tunos, hay que crear las entidades
        
      
        profesional.setModalidad(modalidad);
        profesional.setUbicacion(ubicacion);
        profesional.setHonorario(honorarios);
//        profesional.setDias(dias);
//        profesional.setHoraInicio(horaInicio);
//        profesional.setHoraFin(horaFin);

        profesionalRepositorio.save(profesional);
        }

    }

    public Profesional getOne(String id) {
        return profesionalRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Profesional> listar() {

        List<Profesional> profesionales = new ArrayList();

        profesionales = profesionalRepositorio.findAll();

        return profesionales;
    }

    @Transactional(readOnly = true)
    public List<Profesional> buscarProfesionalesPorEspecialidad(String especialidad) {

        List<Profesional> especialistas; //= new ArrayList();

        especialistas = profesionalRepositorio.buscarPorEspecialidad(especialidad);

        return especialistas;
    }

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
    public void validar(String nombre, String apellido, String correo, String telefono,
            String password, String password2, String especialidad, String ubicacion,
            String modalidad, Double honorarios/*, List<String> obrasSociales, List<String> dias,
            LocalTime horaInicio, LocalTime horaFin*/ ) throws MiExcepcion {

        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new MiExcepcion("El nombre no puede ser nulo o vacío");
            }

            if (apellido == null || apellido.isEmpty()) {
                throw new MiExcepcion("El apellido no puede ser nulo o vacío");
            }

            if (correo == null || correo.isEmpty()) {
                throw new MiExcepcion("El correo electrónico no puede ser nulo o vacío");
            }

            if (telefono == null || telefono.isEmpty()) {
                throw new MiExcepcion("El teléfono no puede ser nulo o vacío");
            }

            if (password == null || password.isEmpty()) {
                throw new MiExcepcion("La contraseña no puede ser nula o vacía");
            }
            if (password2 == null || password2.isEmpty()) {
                throw new MiExcepcion("Es campo no puede ser nulo o vacío");
            }

            if (!(password.equals(password2))) {
                throw new MiExcepcion("Las contraseñas no coinciden");

            }
            if (especialidad == null || especialidad.isEmpty()) {
                throw new MiExcepcion("La especilidad no puede ser nula o vacía");
            }

            if (ubicacion == null || ubicacion.isEmpty()) {
                throw new MiExcepcion("La ubicacion no puede ser nula o vacía");
            }
            
            if (modalidad == null || modalidad.isEmpty()) {
                throw new MiExcepcion("La modalidad no puede ser nula o vacía");
            }

            if (honorarios == null) {
                throw new MiExcepcion("El valor de la consulta no puede ser nulo");
            }

//            if (obrasSociales == null || dias.isEmpty()) {
//                throw new MiExcepcion("Las obras sociales no pueden ser nulas o vacías");
//            }
//            if (dias == null || dias.isEmpty()) {
//                throw new MiExcepcion("Los días no pueden ser nulos o vacíos");
//            }
//            if (horaInicio == null) {
//                throw new MiExcepcion("La hora de inicio no puede ser nula");
//            }
//                if (horaFin == null) {
//                throw new MiExcepcion("Los hora de finalización no puede ser nula");
//            }

        } catch (MiExcepcion ex) {
            throw ex;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Profesional profesional = profesionalRepositorio.buscarPorEmail(email);

        if (profesional != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + profesional.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("profesionalSession", profesional);

            return new User(profesional.getEmail(), profesional.getPassword(), permisos);
        } else {
            return null;
        }

    }

}
