package com.ProyectoFinal.MedicApp.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class HistoriaClinica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
    @ManyToOne
    private Paciente paciente;
    
    @OneToOne
    private Turno turno;
    private String diagnostico;

    public HistoriaClinica() {
    }

    public HistoriaClinica(String id, Paciente paciente, Turno turno, String diagnostico) {
        this.id = id;
        this.paciente = paciente;
        this.turno = turno;
        this.diagnostico = diagnostico;
    }
    
}
