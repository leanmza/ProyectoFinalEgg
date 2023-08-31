package com.ProyectoFinal.MedicApp.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

// CLASE PARA LAS IMAGENES DE LOS DIFERENTES USUARIOS
@Data
@Entity
public class Imagen {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    // IDENTIFICA EL FORMATO DE LA IMAGEN
    private String mime;
    
    private String nombre;
    
    // ALMACENA LOS DATOS BINARIOS DE LA IMAGEN
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    public Imagen() {
    }
}