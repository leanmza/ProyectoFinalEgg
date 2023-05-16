package com.ProyectoFinal.MedicApp.Entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author Lean
 */

// CLASE QUE HEREDA DE "PERSONA" Y AGREGA ATRIBUTOS ESPECIALES PARA EL 
// DESARROLLO DEL PROFESIONAL EN LA PLATAFORMA
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Profesional extends Persona {
    
    // "CLINICA", "PEDIATRIA", "CARDIOLOGIA", ETC
    private String especialidad;

    // PUEDE SER PRESENCIAL O A DISTANCIA
    private String modalidad;

    // SE GUARDA LA ZONA DE TRABAJO
    private String ubicacion;

    private String[] dias;

    // LISTADO DE LAS OBRAS SOCIALES CON LAS CUALES TRABAJA
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
