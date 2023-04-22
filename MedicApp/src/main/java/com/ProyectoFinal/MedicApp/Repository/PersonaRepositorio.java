//package com.ProyectoFinal.MedicApp.Repository;
//
//
//import com.ProyectoFinal.MedicApp.Entity.Persona;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface PersonaRepositorio extends JpaRepository<Persona, String> {
//
//        @Query("SELECT per FROM Persona per WHERE per.email = :email")
//    public Persona buscarPorEmail(@Param("email")String email);
//}