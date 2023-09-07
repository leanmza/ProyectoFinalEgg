/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Entity;

import com.ProyectoFinal.MedicApp.Enum.Rol;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Lean
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public class Persona  {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;

    // UNA PERSONA PUEDE SER "PACIENTE", "PROFESIONAL" O "ADMINISTRADOR"
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    // IMAGEN DE PERFIL, PERMITIENDO SER "NULL"
    @OneToOne
    private Imagen imagen;
    
    // CONTRASEÑA ENCRIPTADA
    private String password;
    
    // ACTIVIDAD DEL USUSARIO, LOS DATOS SE MANTIENEN PARA FUTURO REINGRESO
    private boolean activo = false;

}
