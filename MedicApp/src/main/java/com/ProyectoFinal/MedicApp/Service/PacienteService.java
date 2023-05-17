/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Imagen;
import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Repository.TurnoRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cmoro1
 */
@Service
public class PacienteService implements UserDetailsService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private ImagenService imagenServicio;

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @Autowired
    private ObraSocialService obraSocialServicio;

    @Transactional
    public void crearPaciente(String nombre, String apellido, String dni, String email, String direccion, String telefono,
            String nacimiento, String sexo, String obraSocial, String password, String password2, MultipartFile archivo) throws MiExcepcion, ParseException {

        validar(nombre, apellido, dni, email, direccion, telefono, nacimiento, sexo, obraSocial, password, password2);
        
        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setEmail(email);
        paciente.setDireccion(direccion);
        paciente.setTelefono(telefono);
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimiento = formato.parse(nacimiento);
        paciente.setFechaNacimiento(fechaNacimiento);
        
        paciente.setSexo(sexo);
        
        ObraSocial ClaseObraSocial = obraSocialServicio.getOne(obraSocial);
        paciente.setObraSocial(ClaseObraSocial);
        
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        
        if (!(archivo.isEmpty())) {  //pedimos esto sino nos crea un id para el archivo
            Imagen imagen = imagenServicio.guardar(archivo);
            paciente.setImagen(imagen);
        }

        paciente.setRol(Rol.PACIENTE);
        paciente.setActivo(true);
        pacienteRepositorio.save(paciente);
    }

    @Transactional
    public void modificarPaciente(String id, String nombre, String apellido, String dni, String email, String direccion, String telefono,
            String nacimiento, String sexo, String obraSocial, String password, String password2, MultipartFile archivo) throws MiExcepcion, ParseException {

        validar(nombre, apellido, dni, email, direccion, telefono, nacimiento, sexo, obraSocial, password, password2);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setEmail(email);
            paciente.setDireccion(direccion);
            paciente.setTelefono(telefono);
            
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = formato.parse(nacimiento);
            paciente.setFechaNacimiento(fechaNacimiento);
            
            paciente.setSexo(sexo);
            
            ObraSocial ClaseObraSocial = obraSocialServicio.getOne(obraSocial);
            paciente.setObraSocial(ClaseObraSocial);
            
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));
            
            if (archivo.getSize() == 0) {
                paciente.setImagen(null);
            } else {
                String idImagen = null;
                if (paciente.getImagen() != null) {
                    idImagen = paciente.getImagen().getId();
                }
                Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
                paciente.setImagen(imagen);
            }

            paciente.setRol(Rol.PACIENTE);
            paciente.setActivo(true);
            pacienteRepositorio.save(paciente);
        }
    }

    public Paciente getOne(String id) {
        return pacienteRepositorio.getOne(id);
    }

    public Paciente buscarPorDni(String dni) {
        return pacienteRepositorio.buscarPorDni(dni);
    }

    @Transactional(readOnly = true)
    public List<Paciente> listar() {

        List<Paciente> pacientes = new ArrayList();

        pacientes = pacienteRepositorio.findAll();

        return pacientes;
    }

    public void validar(String nombre, String apellido, String dni, String email, String direccion, String telefono,
            String nacimiento, String sexo, String obraSocial, String password, String password2) throws MiExcepcion {

        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new MiExcepcion("El nombre no puede ser nulo o vacío");
            }

            if (apellido == null || apellido.isEmpty()) {
                throw new MiExcepcion("El apellido no puede ser nulo o vacío");
            }

            if (dni == null || dni.isEmpty()) {
                throw new MiExcepcion("El DNI no puede ser nulo o vacío");
            }
            
            if (pacienteRepositorio.buscarPorDni(dni) != null) {
                    throw new MiExcepcion("El DNI ingresado ya se encuentra registrado");
            }
            
            if (email == null || email.isEmpty()) {
                throw new MiExcepcion("El correo electrónico no puede ser nulo o vacío");
            }
            
            if (pacienteRepositorio.buscarPorEmail(email) != null) {
                throw new MiExcepcion("El correo ingresado ya se encuentra registrado");
            }
            
            if (direccion == null || direccion.isEmpty()) {
                throw new MiExcepcion("La dirección no puede ser nula o vacía");
            }
            
            if (telefono == null || telefono.isEmpty()) {
                throw new MiExcepcion("El teléfono no puede ser nulo o vacío");
            }
            
            if (nacimiento.isEmpty()) {
                throw new MiExcepcion("La fecha no puede ser nula");
            }
            
            if (sexo == null || sexo.isEmpty()) {
                throw new MiExcepcion("El sexo no puede ser nulo o vacío");
            }
            
            if (obraSocial == null) {
                throw new MiExcepcion("Las obras sociales no pueden ser nulas o vacías");
            }
            
            if (password == null || password.isEmpty()) {
                throw new MiExcepcion("La contraseña no puede ser nula o vacía");
            }

            if (password2 == null || password2.isEmpty()) {
                throw new MiExcepcion("La segunda contraseña no puede ser nula o vacía");
            }

            if (!(password.equals(password2))) {
                throw new MiExcepcion("Las contraseñas no coinciden");
            }
        } catch (MiExcepcion ex) {
            throw ex;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Paciente paciente = pacienteRepositorio.buscarPorEmail(email);
        Profesional profesional = profesionalRepositorio.buscarPorEmail(email);

        if (paciente != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + paciente.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("pacienteSession", paciente);

            return new User(paciente.getEmail(), paciente.getPassword(), permisos);

        } else if (profesional != null) {

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

    public void darDeBaja(String id) {
        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();
            paciente.setActivo(Boolean.FALSE);

            pacienteRepositorio.save(paciente);
        }
    }

    @Transactional(readOnly = true)
    public List<Profesional> listarProfesionales(String idPaciente) {
        System.out.println("id paciente " + idPaciente);

        List<Profesional> profesionales;

        profesionales = turnoRepositorio.buscarProfesionalPorPaciente(idPaciente);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);
        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();


            pacienteRepositorio.save(paciente);
        }

        return profesionales;
    }

    @Transactional(readOnly = true)
    public List<Turno> listarTurnos(String idPaciente) {
        System.out.println("id paciente " + idPaciente);

        List<Turno> misTurnos;

        misTurnos = turnoRepositorio.buscarPorPaciente(idPaciente);

        return misTurnos;
    }

    public void calificarProfesional(String id, String puntaje) {
        Integer punt = 0;
        switch (puntaje) {
            case "0":
                punt = 0;
                break;
            case "1":
                punt = 1;
                break;
            case "2":
                punt = 2;
                break;
            case "3":
                punt = 3;
                break;
            case "4":
                punt = 4;
                break;
            case "5":
                punt = 5;
                break;
        }

        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            profesional.setCantVisitas(profesional.getCantVisitas() + 1);
            profesional.setPuntaje(profesional.getPuntaje() + punt);
            profesional.setCalificacion((double) profesional.getPuntaje() / (double) profesional.getCantVisitas());
            profesionalRepositorio.save(profesional);
        }
    }
}
