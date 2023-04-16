package com.ProyectoFinal.MedicApp.Entity;

import com.ProyectoFinal.MedicApp.Enum.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@MappedSuperclass
public class Persona {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    private String foto;
    private String password;
    private boolean activo = false;
//
//    public Persona() {
//    }
//
//    public Persona(String id, String nombre, String apellido, String email, String telefono, Rol rol, String foto, String password, boolean activo) {
//        this.id = id;
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.email = email;
//        this.telefono = telefono;
//        this.rol = rol;
//        this.foto = foto;
//        this.password = password;
//        this.activo = activo;
//    }
}
