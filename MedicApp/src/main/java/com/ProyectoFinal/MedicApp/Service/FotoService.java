package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Foto;
import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ProyectoFinal.MedicApp.Repository.FotoRepositorio;
import java.io.IOException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws MiExcepcion {
        if(archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                
                return fotoRepositorio.save(foto);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return null;
    }
    
    @Transactional
    public Foto actualizar(MultipartFile archivo, String idFoto) throws MiExcepcion {
        if(archivo != null) {
            try {
                Foto foto = new Foto();
                
                if(idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    
                    if(respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                
                return fotoRepositorio.save(foto);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return null;
    }
    
    @Transactional
    public void eliminar(MultipartFile archivo, String idFoto) {
        if(archivo != null) {
            Foto foto = new Foto();
            if(idFoto != null) {
                Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                if(respuesta.isPresent()) {
                    foto = respuesta.get();
                    fotoRepositorio.delete(foto);
                }
            }
        }
    }
}