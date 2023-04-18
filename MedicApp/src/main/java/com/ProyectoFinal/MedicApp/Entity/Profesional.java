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
import javax.persistence.*;

import lombok.Data;

/**
 * @author Lean
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Profesional extends Persona {

    private String especialidad;

    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @Enumerated(EnumType.STRING)
    private Ubicacion ubicacion;

    @Temporal(TemporalType.DATE)
    private Date dias;

    @OneToMany
    private List<ObraSocial> obrasSociales;

    @OneToMany
    private List<Turno> turnos;
    private double honorario;
    @Temporal(TemporalType.TIME)
    private Date horarioFin;
    @Temporal(TemporalType.TIME)
    private Date horarioInicio;
    //    @OneToMany
//    private List<Integer> reputacion;
    private Integer cantVisitas;
    private Integer puntaje;

//    public Profesional() {
//
//    }
//
//
//    public Profesional(String especialidad, Modalidad modalidad, Ubicacion ubicacion, Date horario, Date dias, List<ObraSocial> obrasSociales, List<Turno> turnos,
//            double honorario, /*List<Integer> reputacion,*/ String id, String nombre, String apellido, String email, String telefono, Rol rol, String foto, String password, boolean activo,
//            Integer cantVisitas, Integer puntaje) {
//
//        super(id, nombre, apellido, email, telefono, rol, foto, password, activo);
//        this.especialidad = especialidad;
//        this.modalidad = modalidad;
//        this.ubicacion = ubicacion;
//        this.horario = horario;
//        this.dias = dias;
//        this.obrasSociales = obrasSociales;
//        this.turnos = turnos;
//        this.honorario = honorario;
////        this.reputacion = reputacion;
//        this.cantVisitas = cantVisitas;
//        this.puntaje = puntaje;
//    }

}
