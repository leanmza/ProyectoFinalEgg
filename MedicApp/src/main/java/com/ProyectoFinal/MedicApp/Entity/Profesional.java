package com.ProyectoFinal.MedicApp.Entity;

import com.ProyectoFinal.MedicApp.Enum.Especialidad;
import com.ProyectoFinal.MedicApp.Enum.Modalidad;
import com.ProyectoFinal.MedicApp.Enum.Ubicacion;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Lean
 */

// CLASE QUE HEREDA DE "PERSONA" Y AGREGA ATRIBUTOS ESPECIALES PARA EL 
// DESARROLLO DEL PROFESIONAL EN LA PLATAFORMA
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter

public class Profesional extends Persona {
    
    // "CLINICA", "PEDIATRIA", "CARDIOLOGIA", ETC
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    // PUEDE SER PRESENCIAL O A DISTANCIA
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    // SE GUARDA LA ZONA DE TRABAJO
    @Enumerated(EnumType.STRING)
    private Ubicacion ubicacion;

    private String[] dias;

    // LISTADO DE LAS OBRAS SOCIALES CON LAS CUALES TRABAJA
    @OneToOne
    private ObraSocial obraSocial;

    private Double honorario;


    private LocalTime horaInicio;

    private LocalTime horaFin;
    
    private ArrayList<LocalTime> horas; //no me tomaba el List solo, por eso lo cambié a ArrayList

    private Integer cantVisitas;
    private Integer puntaje;
    private Double calificacion;
}
