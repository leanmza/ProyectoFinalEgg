package com.ProyectoFinal.MedicApp.Repository;


import com.ProyectoFinal.MedicApp.Entity.Foto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends CrudRepository<Foto, Long> {

}