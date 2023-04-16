package com.ProyectoFinal.MedicApp.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ObraSocial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String nombre;

    public ObraSocial() {
    }

    public ObraSocial(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
