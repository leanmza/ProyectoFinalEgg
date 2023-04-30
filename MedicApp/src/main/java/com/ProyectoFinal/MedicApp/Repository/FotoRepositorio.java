package com.ProyectoFinal.MedicApp.Repository;


import com.ProyectoFinal.MedicApp.Entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {

}