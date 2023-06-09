 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Entity;

import java.time.LocalTime;
import java.util.ArrayList;
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

    private String especialidad;


    private String modalidad;


    private String ubicacion;

   
//    private List<String> dias;

    @OneToOne
    private ObraSocial obraSocial;

    private Double honorario;

//    @OneToMany
//    private List<Turno> turnos;

    
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    private ArrayList<String> horas; //no me tomaba el List solo, por eso lo cambié a ArrayList

    private Integer cantVisitas;
    private Integer puntaje;
    private Double calificacion;
}
