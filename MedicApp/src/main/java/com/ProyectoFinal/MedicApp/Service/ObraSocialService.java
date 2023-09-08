/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.ObraSocial;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import com.ProyectoFinal.MedicApp.Repository.ObraSocialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lean
 */
@Service
public class ObraSocialService {

    @Autowired
    private ObraSocialRepositorio obraSocialRepositorio;

    @Transactional
    public void crearObraSocial(String nombre) throws MiExcepcion {

        validar(nombre);
        
        ObraSocial obraSocial = new ObraSocial();

        obraSocial.setNombre(nombre);

        obraSocialRepositorio.save(obraSocial);

        }

    @Transactional
    public void modificarObraSocial(String idObraSocial, String nombre) throws MiExcepcion {

        validar(nombre);

        Optional<ObraSocial> respuesta = obraSocialRepositorio.findById(idObraSocial);

        if (respuesta.isPresent()) {

            ObraSocial obraSocial = new ObraSocial();

            obraSocial.setNombre(nombre);

            obraSocialRepositorio.save(obraSocial);

        }
    }
    
    @Transactional
    public ObraSocial cambiarObraSocial(ObraSocial obraSocial, String idObraSocial) {
        if(obraSocial != null) {
            ObraSocial obraSocialActualizada = new ObraSocial();
            if(idObraSocial != null) {
                Optional<ObraSocial> respuesta = obraSocialRepositorio.findById(idObraSocial);
                if(respuesta.isPresent()){
                    obraSocialActualizada = respuesta.get();
                }
            }
            obraSocialActualizada.setNombre(obraSocial.getNombre());
            return obraSocialRepositorio.save(obraSocialActualizada);
        }
        return null;
    }

    public ObraSocial getOne(String id) {
        return obraSocialRepositorio.getOne(id);
    }

    public ObraSocial buscarPorNombre(String nombre) {
        return obraSocialRepositorio.buscarPorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public List<ObraSocial> listar() {

        List<ObraSocial> obrasSociales = new ArrayList();

        obrasSociales = obraSocialRepositorio.findAll();

        return obrasSociales;
    }

    public void validar(String nombre) throws MiExcepcion {

        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new MiExcepcion("El nombre de la obra social no puede ser nulo o vacío");
            }
        } catch (MiExcepcion ex) {
            throw ex;
        }

    }

}
