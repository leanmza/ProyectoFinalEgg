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

@Data
@Entity
public class HistoriaClinica {
    
   @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @ManyToOne
    private Paciente paciente;
        
   @Temporal(TemporalType.DATE)
    private Date fechaConsulta;
   
   @OneToOne
   private Profesional profesional; //Sacamos nombre y especialidad
   
    private String diagnostico;

    public HistoriaClinica() {
    }
    
    public HistoriaClinica(String id, Paciente paciente, Date fechaConsulta, Profesional profesional, String diagnostico) {
        this.id = id;
        this.paciente = paciente;
        this.fechaConsulta = fechaConsulta;
        this.profesional = profesional;
        this.diagnostico = diagnostico;
    }


    
}
