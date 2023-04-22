package com.ProyectoFinal.MedicApp.Entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Turno {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @ManyToOne
    Profesional profesional;
    
    @ManyToOne
    Paciente paciente;

    public Turno() {
    }

    public Turno(String id, Date fecha, Profesional profesional, Paciente paciente) {
        this.id = id;
        this.fecha = fecha;
        this.profesional = profesional;
        this.paciente = paciente;
    }
}
