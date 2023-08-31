package com.ProyectoFinal.MedicApp.service;

import com.ProyectoFinal.MedicApp.entity.Imagen;
import com.ProyectoFinal.MedicApp.exception.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.ProyectoFinal.MedicApp.repository.ImagenRepositorio;

@Service
public class ImagenService {
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MiExcepcion {
        if(archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return null;
    }
    
    @Transactional
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiExcepcion {
        if(archivo != null) {
            try {
                Imagen imagen = new Imagen();
                
                if(idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    
                    if(respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return null;
    }
    
    @Transactional
    public void eliminar(MultipartFile archivo, String idImagen) {
        if(archivo != null) {
            Imagen imagen = new Imagen();
            if(idImagen != null) {
                Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                if(respuesta.isPresent()) {
                    imagen = respuesta.get();
                    imagenRepositorio.delete(imagen);
                }
            }
        }
    }
}