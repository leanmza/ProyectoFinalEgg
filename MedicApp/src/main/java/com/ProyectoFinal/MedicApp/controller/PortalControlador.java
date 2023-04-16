package com.ProyectoFinal.MedicApp.controller;


import com.ProyectoFinal.MedicApp.Entity.Profesional;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Service.ProfesionalServicio;
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
    ProfesionalServicio profesionalService;
    
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo, HttpSession session) {
        
        List<Profesional> profesionales = profesionalService.listar();
        modelo.put("profesionales", profesionales);
        
        return "index.html";
    }
    
    @GetMapping("/registrarProfesional")
    public String registrarProfesional() {
        return "formulario_profesional.html"; //ver nombre de archivo
    }
    
    @PostMapping("/regitroProfesional")
    public String registroProfesional(@RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String email, @RequestParam String telefono,
            MultipartFile archivo, @RequestParam String password, @RequestParam String password2,
            @RequestParam String especialidad, @RequestParam Modalidad modalidad,
            @RequestParam Ubicacion ubicacion, @RequestParam Date horarioInicio,
            @RequestParam Date horarioFin, @RequestParam Date dias, @RequestParam List<ObraSocial> obrasSociales,
            @RequestParam Double honorarios) {
        
        try {
            profesionalService.crearProfesional(nombre, apellido, email, telefono, archivo,
                    password, password2, especialidad, modalidad, ubicacion, horarioInicio,
                    horarioFin, dias, obrasSociales, honorarios);
            System.out.println("Ingreso exitoso");
            return "redirec:/inicio";
        } catch (MiExcepcion me) {
            System.out.println("Ingreso fallido\n" + me.getMessage());
            return "formulario_profesional.html";
        }
    }
}