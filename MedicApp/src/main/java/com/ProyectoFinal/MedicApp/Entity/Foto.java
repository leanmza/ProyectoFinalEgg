package com.ProyectoFinal.MedicApp.Entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
public class Foto {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String mime;
    private String nombre;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    public Foto(byte[] imagen) {
        this.contenido = imagen;
    }

    public Foto() {
    }

    public Foto(String id, String mime, String nombre, byte[] contenido) {
        this.id = id;
        this.mime = mime;
        this.nombre = nombre;
        this.contenido = contenido;
    }
}