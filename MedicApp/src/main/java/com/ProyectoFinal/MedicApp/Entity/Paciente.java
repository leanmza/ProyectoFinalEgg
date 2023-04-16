package com.ProyectoFinal.MedicApp.Entity;

import com.ProyectoFinal.MedicApp.Enum.Rol;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Paciente extends Persona {
    
    private String direccion;
    
    @OneToMany
    private List<HistoriaClinica> historiaClinica;
    
    @OneToOne
    private ObraSocial obraSocial;
    
    @OneToMany
    private List<Turno> turnos;
    
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String sexo;

//    public Paciente() {
//    }
//
//    public Paciente(String direccion, List<HistoriaClinica> historiaClinica, ObraSocial obraSocial, 
//            List<Turno> turnos, Date fechaNacimiento, String sexo,  String nombre, 
//            String apellido, String email, String telefono, Rol rol, String foto, 
//            String password, boolean activo) {
//        this.direccion = direccion;
//        this.historiaClinica = historiaClinica;
//        this.obraSocial = obraSocial;
//        this.turnos = turnos;
//        this.fechaNacimiento = fechaNacimiento;
//        this.sexo = sexo;
//    }
    
}
