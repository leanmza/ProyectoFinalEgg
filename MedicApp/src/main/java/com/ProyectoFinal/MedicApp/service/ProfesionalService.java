/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.service;

import com.ProyectoFinal.MedicApp.entity.Imagen;
import com.ProyectoFinal.MedicApp.entity.ObraSocial;
import com.ProyectoFinal.MedicApp.entity.Profesional;
import com.ProyectoFinal.MedicApp.entity.Turno;
import com.ProyectoFinal.MedicApp.enums.Modalidad;
import com.ProyectoFinal.MedicApp.enums.Rol;
import com.ProyectoFinal.MedicApp.enums.Ubicacion;
import com.ProyectoFinal.MedicApp.exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.repository.ProfesionalRepositorio;
import com.ProyectoFinal.MedicApp.repository.TurnoRepositorio;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Lean
 */
@Service
public class ProfesionalService{

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private ImagenService imagenServicio;

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    @Transactional
    public void crearProfesional(String nombre, String apellido, String correo, String telefono,
            MultipartFile archivo, String password, String password2, String especialidad, String ubicacion,
            String modalidad, Double honorarios, ObraSocial obraSocial, String[] dias, LocalTime horaInicio,
            LocalTime horaFin /*List<ObrasSociales> obrasSociales*/) throws MiExcepcion {                       /// HACER FUNCIONAR LISTA DE OBRA SOCIALES

        validar(nombre, apellido, correo, telefono, password, password2, especialidad, ubicacion,
                modalidad, honorarios, obraSocial, dias, horaInicio, horaFin);

        Profesional profesional = new Profesional();

        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setEmail(correo);
        profesional.setTelefono(telefono);

        if (!(archivo.isEmpty())) {  //pedimos esto sino nos crea un id para el archivo
            Imagen imagen = imagenServicio.guardar(archivo);
            profesional.setImagen(imagen);
        }

        profesional.setPassword(new BCryptPasswordEncoder().encode(password));

        profesional.setRol(Rol.PROFESIONAL);

        profesional.setActivo(true);
        profesional.setEspecialidad(especialidad);

        profesional.setModalidad(Modalidad.valueOf(modalidad));

        profesional.setUbicacion(Ubicacion.valueOf(ubicacion));

        profesional.setHonorario(honorarios);

        ///TIENE QUE SER UN ARRAYLIST DE OBRAS SOCIALES
        profesional.setObraSocial(obraSocial);

        // CREAMOS LA LISTA DE HORARIOS SEPARADOS CADA 30 MINUTOS
        ArrayList<String> horario = new ArrayList();

        System.out.println("horario" + horario);

        while (horaInicio.isBefore(horaFin)) {
            horario.add(horaInicio.format(DateTimeFormatter.ofPattern("HH:mm")));
            horaInicio = horaInicio.plusMinutes(30);
            System.out.println("Hora inicio" + horaInicio);
        }

        for (String horas : horario) {
            System.out.println("horas" + horas);
        }

        profesional.setHoras(horario);

        // CREAMOS UN HASHMAP CON LOS DIAS DE TRABAJO Y LE INSERTAMOS EL HORARIO DE TRABAJO
        profesional.setDias(dias);

        profesional.setHoraInicio(horaInicio);
        profesional.setHoraFin(horaFin);
        profesional.setCantVisitas(0);
        profesional.setPuntaje(0);
        profesional.setCalificacion(0.0);

        profesionalRepositorio.save(profesional);

    }

    @Transactional
    public void modificarProfesional(String idProfesional, String nombre, String apellido, String correo,
            String telefono, MultipartFile archivo, String password, String password2, String especialidad,
            String ubicacion, String modalidad, Double honorarios, ObraSocial obraSocial, String[] dias,
            LocalTime horaInicio, LocalTime horaFin/*, List<ObrasSociales> obrasSociales*/) throws MiExcepcion {

        validar(nombre, apellido, correo, telefono, password, password2, especialidad, ubicacion,
                modalidad, honorarios, obraSocial, dias, horaInicio, horaFin);

        Optional<Profesional> respuesta = profesionalRepositorio.findById(idProfesional);

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setEmail(correo);
            profesional.setTelefono(telefono);

            if (archivo.getSize() == 0) {
                profesional.setImagen(null);
            } else {
                String idImagen = null;
                if (profesional.getImagen() != null) {
                    idImagen = profesional.getImagen().getId();
                }
                Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
                profesional.setImagen(imagen);
            }

            profesional.setPassword(new BCryptPasswordEncoder().encode(password));
            profesional.setActivo(true);
            profesional.setEspecialidad(especialidad);

            // NO SETEAMOS ROL PORQUE MANTIENE EL QUE TENIA AL MODIFICAR PERFIL 
            //Falta ObrasSociales y Tunos, hay que crear las entidades
            profesional.setModalidad(Modalidad.valueOf(modalidad));
            profesional.setUbicacion(Ubicacion.valueOf(ubicacion));
            profesional.setHonorario(honorarios);

            ///TIENE QUE SER UN ARRAYLIST DE OBRAS SOCIALES
            profesional.setObraSocial(obraSocial);
//        profesional.setDias(dias);
            profesional.setHoraInicio(horaInicio);
            profesional.setHoraFin(horaFin);

/////// CARGA EL ARRAYLIST DE HORAS //////
            ArrayList<String> horario = new ArrayList();
            System.out.println("horario" + horario);
            while (horaInicio.isBefore(horaFin)) {
                horario.add(horaInicio.format(DateTimeFormatter.ofPattern("HH:mm")));
                horaInicio = horaInicio.plusMinutes(30);

            }
            for (String horas : horario) { ///PARA VER QUE FUNCIONA
                System.out.println("horas" + horas);
            }

            profesional.setHoras(horario);
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

    @Transactional(readOnly = true)
    public List<Profesional> buscarProfesionalesPorEspecialidadOrdenadoHonorario(String especialidad) {

        List<Profesional> especialistas; //= new ArrayList();

        especialistas = profesionalRepositorio.buscarPorEspecialidadOrdHonorario(especialidad);

        return especialistas;
    }

    @Transactional(readOnly = true)
    public List<Profesional> buscarProfesionalesPorEspecialidadOrdenadoCalificacion(String especialidad) {

        List<Profesional> especialistas; //= new ArrayList();

        especialistas = profesionalRepositorio.buscarPorEspecialidadOrdCalificacion(especialidad);

        return especialistas;
    }

    public void validar(String nombre, String apellido, String correo, String telefono,
            String password, String password2, String especialidad, String ubicacion,
            String modalidad, Double honorarios, ObraSocial obraSocial, String[] dias,
            LocalTime horaInicio, LocalTime horaFin) throws MiExcepcion {
       
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

            if (obraSocial == null) {
                throw new MiExcepcion("Las obras sociales no pueden ser nulas o vacías");
            }
            if (dias == null) {
                throw new MiExcepcion("Los días no pueden ser nulos o vacíos");
            }
            if (horaInicio == null) {
                throw new MiExcepcion("La hora de inicio no puede ser nula");
            }
            if (horaFin == null) {
                throw new MiExcepcion("Los hora de finalización no puede ser nula");
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

    private boolean validarNumero(String password) {
        boolean cumple = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                cumple = true;
                System.out.println("tiene numero");
                break;
            }
        }
        return cumple;
    }

    private boolean validarMayuscula(String password) {
        boolean cumple = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                System.out.println("tiene mayuscula");
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
                System.out.println("tiene minuscula");
                cumple = true;
                break;
            }
        }
        return cumple;
    }

    @Transactional(readOnly = true)
    public List<Turno> listarTurnos(String idProfesional) {
        System.out.println("id del profesional " + idProfesional);

        List<Turno> misTurnos;

        misTurnos = turnoRepositorio.buscarPorProfesional(idProfesional);

        return misTurnos;
    }

}
