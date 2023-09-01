package com.ProyectoFinal.MedicApp.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// CLASE QUE HEREDA DE "PERSONA" Y AGREGA ATRIBUTOS ESPECIALES PARA EL 
// DESARROLLO DEL PACIENTE EN LA PLATAFORMA
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
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
    private LocalDate fechaNacimiento;
    
    private String sexo;


    
}
