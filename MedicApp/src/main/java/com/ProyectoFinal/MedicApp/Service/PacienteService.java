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
import com.ProyectoFinal.MedicApp.Repository.ObraSocialRepositorio;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.Repository.TurnoRepositorio;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cmoro1
 */
@Service
public class PacienteService {

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

    @Autowired
    private ObraSocialRepositorio obraSocialRepositorio;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(nacimiento, formatter);
        paciente.setFechaNacimiento(fechaNacimiento);

        paciente.setSexo(sexo);

        paciente.setObraSocial(obraSocialServicio.getOne(obraSocial));

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
    public void modificarPaciente(String id, String nombre, String apellido, String direccion, String telefono,
            String sexo, String obraSocial, String password, String password2, MultipartFile archivo) throws MiExcepcion, ParseException {

        validarModificar(nombre, apellido, direccion, telefono, sexo, obraSocial, password, password2);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDireccion(direccion);
            paciente.setTelefono(telefono);

            paciente.setSexo(sexo);

            ObraSocial obraSoci = obraSocialRepositorio.buscarPorNombre(obraSocial);

            paciente.setObraSocial(obraSoci);

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

    @Transactional(readOnly = true) //NO SE USA, hacer una vista para el administrador
    public List<Paciente> listar() {

        List<Paciente> pacientes = new ArrayList();

        pacientes = pacienteRepositorio.findAll();

        return pacientes;
    }

    public void validar(String nombre, String apellido, String dni, String email, String direccion, String telefono,
            String nacimiento, String sexo, String obraSocial, String password, String password2) throws MiExcepcion {
        // Primero comprobamos que el email y el DNI no se encuentren ya almacenados en la BD
        if (emailChecker(email) == true) {
            throw new MiExcepcion("El email " + email + " ya se encuentra registrado");
        }

        if (dniChecker(dni) == true) {
            throw new MiExcepcion("El DNI ingresado ya se encuentra registrado");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede ser nulo o vacío");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("El apellido no puede ser nulo o vacío");
        }

        if (dni == null || dni.isEmpty()) {
            throw new MiExcepcion("El DNI no puede ser nulo o vacío");
        }

        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El correo electrónico no puede ser nulo o vacío");
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
        if (validarNumero(password) == false) {
            throw new MiExcepcion("La contraseña tiene que tener al menos un un número");
        }
        if (validarMayuscula(password) == false) {
            throw new MiExcepcion("La contraseña tiene que tener al menos una mayúscula");
        }
        if (validarMinuscula(password) == false) {
            throw new MiExcepcion("La contraseña tiene que tener al menos una minúscula");
        }

    }

    //  VALIDA LOS DATOS QUE ESTAN HABILITADOS PARA MODIFICAR, NO SE REVISA SI EL EMIAL O EL DNI YA EXISITEN
    public void validarModificar(String nombre, String apellido, String direccion, String telefono,
            String sexo, String obraSocial, String password, String password2) throws MiExcepcion {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede ser nulo o vacío");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("El apellido no puede ser nulo o vacío");
        }

        if (direccion == null || direccion.isEmpty()) {
            throw new MiExcepcion("La dirección no puede ser nula o vacía");
        }

        if (telefono == null || telefono.isEmpty()) {
            throw new MiExcepcion("El teléfono no puede ser nulo o vacío");
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
        if (validarNumero(password) == false) {
            throw new MiExcepcion("La contraseña tiene que tener al menos un un número");
        }
        if (validarMayuscula(password) == false) {
            throw new MiExcepcion("La contraseña tiene que tener al menos una mayúscula");
        }
        if (validarMinuscula(password) == false) {
            throw new MiExcepcion("La contraseña tiene que tener al menos una minúscula");
        }

    }

    private boolean emailChecker(String email) { // Verifica si el email ya existe en la BD
        boolean check = false;
        Paciente paciente = pacienteRepositorio.buscarPorEmail(email);
        if (paciente != null) {
            check = true;
        }
        return check;
    }

    private boolean dniChecker(String dni) { // Verifica si el email ya existe en la BD
        boolean check = false;
        Paciente paciente = pacienteRepositorio.buscarPorDni(dni);
        if (paciente != null) {
            check = true;
        }
        return check;
    }

    private boolean validarNumero(String password) {
        boolean cumple = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                cumple = true;

                break;
            }
        }
        return cumple;
    }

    private boolean validarMayuscula(String password) {
        boolean cumple = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {

                cumple = true;
                break;
            }
        }
        return cumple;
    }

    private boolean validarMinuscula(String password) {
        boolean cumple = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {

                cumple = true;
                break;
            }
        }
        return cumple;
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

        List<Turno> misTurnos = turnoRepositorio.buscarPorPaciente(idPaciente);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Turno turno : misTurnos) {

            LocalDate fecha = turno.getFecha();
            String fechaFormateada = fecha.format(formatter);
            turno.setFechaFormateada(fechaFormateada);
        }

        return misTurnos;
    }

    public void calificarProfesional(String id, int puntaje) {
//SAQUÉ EL SWITCH Y CAMBIÉ EL TIPO DE DATO DE STRING A INT, FUNCIONA Y QUEDA MÁS LIMPIO EL CÓDIGO

        Optional<Profesional> respuesta = profesionalRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();

            profesional.setCantVisitas(profesional.getCantVisitas() + 1);

            profesional.setPuntaje(profesional.getPuntaje() + puntaje);

            profesional.setCalificacion((double) profesional.getPuntaje() / (double) profesional.getCantVisitas());

            profesionalRepositorio.save(profesional);
        }
    }
}
