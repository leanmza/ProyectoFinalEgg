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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Lean
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Profesional extends Persona {
    
    private  String especialidad;
    
//    @OneToOne
    private Modalidad modalidad;
    
//    @OneToOne
    private Ubicacion ubicacion;
    
    @Temporal(TemporalType.TIME)
    private Date horario;
    
    @Temporal(TemporalType.DATE)
    private Date dias;
    
    @OneToMany
    private List<ObraSocial> obrasSociales;
    
    @OneToMany
    private List<Turno> turnos;  
    private double honorario;
//    @OneToMany
//    private List<Integer> reputacion;
    private Integer cantVisitas;
    private Integer puntaje;


}
