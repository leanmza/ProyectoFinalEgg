package com.ProyectoFinal.MedicApp.Service;

import com.ProyectoFinal.MedicApp.Entity.Foto;
import com.ProyectoFinal.MedicApp.Repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoService {
    @Autowired
    private FotoRepository fotoRepository;

    public void guardarFoto(byte[] imagen) {
        Foto foto = new Foto(imagen);
        fotoRepository.save(foto);
    }
}