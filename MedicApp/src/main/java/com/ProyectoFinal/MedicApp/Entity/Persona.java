package com.ProyectoFinal.MedicApp.Entity;

import com.ProyectoFinal.MedicApp.Enum.Rol;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Persona {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String foto;
    private String password;
    private boolean activo=false;

    public Persona() {
    }

    public Persona(Long id, String nombre, String apellido, String email, String telefono, Rol rol, String foto, String password, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.foto = foto;
        this.password = password;
        this.activo = activo;
    }
}

 
