package com.ProyectoFinal.MedicApp.Entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

// CLASE QUE HEREDA DE "PERSONA" Y AGREGA ATRIBUTOS ESPECIALES PARA EL 
// DESARROLLO DEL PACIENTE EN LA PLATAFORMA
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Paciente extends Persona {
    
    private String direccion;
    
    // UN PACIENTE DISPONE DE UNA O MAS HISTORIAS CLINICAS
    @OneToMany
    private List<HistoriaClinica> historiaClinica;
    
    // EN CASO DE SER "NULL" SE TOMA COMO QUE NO DISPONE DE UNA
    @OneToOne
    private ObraSocial obraSocial;
    
    // UN PACIENTE PUEDE TENER VARIOS TURNOS PEDIDOS
    @OneToMany
    private List<Turno> turnos;
    
    // ATRIBUTO UTLIZADO PARA CALCULAR LA EDAD DEL PACIENTE
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    
    private String sexo;


    
}
