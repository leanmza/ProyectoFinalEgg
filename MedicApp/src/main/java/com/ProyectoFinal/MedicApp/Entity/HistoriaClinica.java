package com.ProyectoFinal.MedicApp.Entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

// CLASE QUE GENERA LAS HISTORIAS CLINICAS DE LOS PACIENTES, Y SON CREADAS
// POR LOS PROFESIONALES
@Data
@Entity
public class HistoriaClinica {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    // OBTENER LOS DATOS DEL PACIENTE AL QUE PERTENECE LA HISTORIA CLINICA
    // UN PACIENTE PUEDE TENER MUCHAS HOSTRIAS CLINICAS
    @ManyToOne
    private Paciente paciente;

    // GUARDAR LA FECHAM EN LA QUE SE CARGA LA HOSTORIA CLINICA
    @Temporal(TemporalType.DATE)
    private Date fechaConsulta;

    // OBTENER LOS DATOS DEL PROFESIONAL QUE CREO LA HISTORIA CLINICA
    @OneToOne
    private Profesional profesional;

    // GUARDA EL DIAGNOSTICO DEL PROFESIONAL
    private String diagnostico;
    
    // GUARDA EL TRATAMIENTO BRINDADO POR EL PROFESIONAL
    private String tratamiento;

}
