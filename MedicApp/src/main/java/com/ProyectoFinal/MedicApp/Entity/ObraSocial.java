package com.ProyectoFinal.MedicApp.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class ObraSocial {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;

    public ObraSocial() {
    }

    public ObraSocial(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
