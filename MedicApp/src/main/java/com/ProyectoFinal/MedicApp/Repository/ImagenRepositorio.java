package com.ProyectoFinal.MedicApp.Repository;


import com.ProyectoFinal.MedicApp.Entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {

}