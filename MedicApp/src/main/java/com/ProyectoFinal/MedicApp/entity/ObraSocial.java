package com.ProyectoFinal.MedicApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

// CLASE PARA LAS OBRAS SOCIALES CON LAS QUE TRABAJARAN LOS 
// PROFESIONALES Y DE LAS QUE DISPONEN LOS USUARIOS
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

}
