/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Entity.Imagen;
import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.HistoriaClinica;
import com.ProyectoFinal.MedicApp.Entity.Paciente;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.PacienteRepositorio;
import com.ProyectoFinal.MedicApp.Service.ImagenService;
import com.ProyectoFinal.MedicApp.Service.ObraSocialService;
import com.ProyectoFinal.MedicApp.Repository.TurnoRepositorio;
import com.ProyectoFinal.MedicApp.Service.PacienteService;
import com.ProyectoFinal.MedicApp.Service.TurnoService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cmoro1
 */
@Controller
@PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
@RequestMapping("/pac")
public class PacienteControlador {

    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Autowired
    PacienteService pacienteService;
    
    @Autowired
    ObraSocialService obraSocialServicio;
    
    @Autowired
    ImagenService imagenServicio;

    @Autowired
    TurnoService turnoService;

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMINISTRADOR')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session, HttpSession obraSocialNueva) {
        
        // CARGAMOS LOS DATOS DEL PACIENTE
        Paciente paciente = (Paciente) session.getAttribute("pacienteSession");
        // EN EL CASO QUE HAYA AGREGADO UNA OBRA SOCIAL NUEVA, ACTUALIZAMOS LA LISTA DE OBRAS SOCIALES SETEADAS
        if (obraSocialNueva.getAttribute("nuevaObraSocial") != null) {
            String nombreOS = (String) obraSocialNueva.getAttribute("nuevaObraSocial");
            ObraSocial obraSocial = obraSocialServicio.buscarPorNombre(nombreOS);
            paciente.setObraSocial(obraSocial);
        }
        modelo.put("paciente", paciente);
        
        // CARFA DE LAS OBRAS SOCIALES
        List<ObraSocial> obrasSociales = obraSocialServicio.listar();
        modelo.put("obrasSociales", obrasSociales);
        
        return "editar_paciente.html";
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMINISTRADOR')")
    @PostMapping("/perfil/{id}")
    public String modificarPerfil(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String dni, @RequestParam String correo, @RequestParam String telefono,
            @RequestParam(required = false) String nacimiento, @RequestParam(required = false) String password, @RequestParam(required = false) String password2, 
            @RequestParam String direccion, @RequestParam String sexo, @RequestParam(required = false) MultipartFile archivo, @RequestParam String obraSocial,
            HttpSession session, ModelMap modelo ) {
       
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd
            Date fechaNacimiento = formato.parse(nacimiento);
            
            ObraSocial claseObraSocial = obraSocialServicio.buscarPorNombre(obraSocial); // A PARTIR DEL ID BUSCAMOS LA CLASE OBRAsOCIAL
            
            pacienteService.modificarPaciente(id, nombre, apellido, dni, correo, telefono, password, password2, direccion, 
                    fechaNacimiento, sexo, archivo, claseObraSocial);
            session.setAttribute("pacienteSession", pacienteService.getOne(id));
            return "redirect:/inicio";

        } catch (MiExcepcion me) {
            System.out.println("Ingreso de paciente FALLIDO!\n" + me.getMessage());

            return "editar_paciente.html";

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "editar_paciente.html";
        }
    }

    @Transactional
    @GetMapping("/baja/{id}")
    public String bajaPaciente(@PathVariable String id) {

        pacienteService.darDeBaja(id);

        return "redirect:/";
    }
    
    @Transactional
    @PostMapping("/guardarDatosFormulario")
    public String guardarDatosFormulario (@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String dni, @RequestParam(required = false) String email, @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String password, @RequestParam(required = false) String password2, @RequestParam(required = false) String direccion,
            @RequestParam(required = false) Date fechaNacimiento, @RequestParam(required = false) String sexo,
            @RequestParam(required = false) MultipartFile archivo, @RequestParam(required = false) String obraSocial, HttpSession sessionFormulario) throws MiExcepcion {
        
        ObraSocial ClaseObraSocial = obraSocialServicio.buscarPorNombre(obraSocial);
        
        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        paciente.setDireccion(direccion);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setSexo(sexo);
        paciente.setObraSocial(ClaseObraSocial);
        
        if(!(archivo.isEmpty())) {  //pedimos esto sino nos crea un id para el archivo
            Imagen imagen = imagenServicio.guardar(archivo);
            paciente.setImagen(imagen);
        }
        
        sessionFormulario.setAttribute("datosFormulario", paciente);
        
        return "redirect:/pac/perfil";
    }

    @Transactional
    @GetMapping("/misProfesionales")
    public String listaProfesionales(ModelMap model, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("pacienteSession");
        String idPaciente = paciente.getId();

        List<Profesional> profesionales = pacienteService.listarProfesionales(idPaciente);

        model.addAttribute("profesionales", profesionales);
        return "mis_profesionales.html";
    }

    @Transactional
    @GetMapping("/misTurnos")
    public String listaTurnos(ModelMap model, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("pacienteSession");
        String idPaciente = paciente.getId();

        List<Turno> turnos = pacienteService.listarTurnos(idPaciente);

        model.addAttribute("turnos", turnos);
        return "mis_turnos.html";
    }

    @Transactional
    @GetMapping("/anularTurno/{id}")
    public String anularTurno(@PathVariable String id) throws MiExcepcion {

        System.out.println("    id" + id);
        turnoService.eliminarTurno(id);
        return "redirect:/pac/misTurnos";
    }


    @Transactional
    @PostMapping("/calificarProfesional/{id}")
    public String calificarProfesional(@PathVariable("id") String id, @RequestParam("puntaje") String puntaje) {
        pacienteService.calificarProfesional(id, puntaje);
        return "redirect:/pac/misProfesionales";
    }

}
