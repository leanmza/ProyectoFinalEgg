/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Entity;

import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Rol;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Lean
 */

@Data
@Entity

public class Profesional extends Persona {
    
    private  String especialidad;
    private Modalidad modalidad;
    private Ubicacion ubicacion;
    @Temporal(TemporalType.TIME)
    private Date horario;
    @Temporal(TemporalType.DATE)
    private Date dias;
//    private List<ObraSocial> obrasSociales; //FALTA CREAR CLASE ObraSocial
//    private List<Turno> turnos;  //FALTA CREAR CLASE Turno
    private double honorario;
    private List<Integer> reputacion;

    public Profesional() {

    }

    public Profesional(String especialidad, Modalidad modalidad, Ubicacion ubicacion, Date horario, Date dias, /*List<ObraSocial> obrasSociales, List<Turno> turnos,*/ double honorario, List<Integer> reputacion, Long id, String nombre, String apellido, String email, String telefono, Rol rol, String foto, String password, boolean activo) {
        super(id, nombre, apellido, email, telefono, rol, foto, password, activo);
        this.especialidad = especialidad;
        this.modalidad = modalidad;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.dias = dias;
//        this.obrasSociales = obrasSociales;
//        this.turnos = turnos;
        this.honorario = honorario;
        this.reputacion = reputacion;
    }
    
    
}



