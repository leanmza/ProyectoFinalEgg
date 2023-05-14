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

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Paciente extends Persona {
    
    private String direccion;
    
    @OneToMany
    private List<HistoriaClinica> historiaClinica;
    
    @OneToOne
    private ObraSocial obraSocial;
    
    @OneToMany
    private List<Turno> turnos;
    
    @OneToMany
    private List<Profesional> profesionales;
    
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String sexo;


    
}
