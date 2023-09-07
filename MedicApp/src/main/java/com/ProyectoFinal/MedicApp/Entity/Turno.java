package com.ProyectoFinal.MedicApp.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Turno {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private LocalDate fecha; // Siempre guarda la fecha con formato yyyy-MM-dd

    private LocalTime hora;

    @OneToOne
    private Profesional profesional;

    @OneToOne
    private Paciente paciente;

    private String motivo;

    @Transient //Sirve para que el atributo no se persista en la BD
    private String FechaFormateada; // Se usa para mostrar la fecha formateada con dd-MM-yyyy en el HTML

}
