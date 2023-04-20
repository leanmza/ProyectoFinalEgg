package com.ProyectoFinal.MedicApp.Entity;

import javax.persistence.Entity;
import lombok.Data;

/**
 *
 * @author Ariel
 */

@Entity
@Data
public class Administrador {
    
    private String email;
    private String password;

    public Administrador() {
    }

    public Administrador(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
