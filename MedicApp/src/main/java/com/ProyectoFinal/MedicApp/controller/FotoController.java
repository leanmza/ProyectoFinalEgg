package com.ProyectoFinal.MedicApp.controller;

import com.ProyectoFinal.MedicApp.Service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/subir")
public class FotoController {
    @Autowired
    private FotoService fotoService;

    @GetMapping("/Fotito")
    private String subirFoto(){ return "subirFoto.html";}

    @PostMapping("/foto")
    public String subirFoto(@RequestParam("imagen") MultipartFile archivo) {
        try {
            fotoService.guardarFoto(archivo.getBytes());
            return "index.html";
        } catch (IOException e) {
            e.printStackTrace();
            return "subirFoto.html";
        }
    }
}
