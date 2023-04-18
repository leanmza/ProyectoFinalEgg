package com.ProyectoFinal.MedicApp.Entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] imagen;

    public Foto(byte[] imagen) {
        this.imagen = imagen;
    }

}