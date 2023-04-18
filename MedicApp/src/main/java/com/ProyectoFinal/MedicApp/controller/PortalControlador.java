package com.ProyectoFinal.MedicApp.controller;


import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Entity.Turno;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ProfesionalService;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    ProfesionalService profesionalService;              //Agregado por Claudio el 16/04 - 17:40
    
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session) {
        
        List<Profesional> profesionales = profesionalService.listar();
        modelo.put("profesionales", profesionales);
        
        return "index.html";
    }
    
<<<<<<< HEAD

=======
    @GetMapping("/registrarProfesional")
    public String registrarProfesional() {
        return "formulario_profesional.html"; //ver nombre de archivo
    }
    
    @PostMapping("/regitroProfesional")
    public String registroProfesional(@RequestParam String nombre, @RequestParam String apellido, 
            @RequestParam String email, @RequestParam String telefono,
            /*MultipartFile archivo,*/ @RequestParam String password, @RequestParam String password2,
            @RequestParam String especialidad, @RequestParam String modalidad,
            @RequestParam String ubicacion, /*@RequestParam Date horarioInicio,
            @RequestParam Date horarioFin,*/@RequestParam Date horario, @RequestParam Date dias,
            /*@RequestParam List<ObraSocial> obrasSociales, @RequestParam List<Turno> turnos,*/
            @RequestParam Double honorarios) {
        try {
            profesionalService.crearProfesional(nombre, apellido, email, telefono, /*archivo,*/
                    password, password2, especialidad, modalidad, ubicacion,/* horarioInicio,
                    horarioFin,*/horario, dias, /*obrasSociales,*/ honorarios);
            System.out.println("Ingreso exitoso");
            return "redirec:/inicio";
        } catch (MiExcepcion me) {
            System.out.println("Ingreso fallido\n" + me.getMessage());
            return "formulario_profesional.html";
        }
    }
>>>>>>> 2c1d50f0b2b11d1562299ab5d3aa9ec8e6662e69
}