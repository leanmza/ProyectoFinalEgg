/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoFinal.MedicApp.service;

import com.ProyectoFinal.MedicApp.entity.Persona;
import com.ProyectoFinal.MedicApp.repository.PersonaRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Lean
 */

@Service
public class PersonaService implements UserDetailsService {
    
        @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public Persona getOne(String id) {
        return personaRepository.getOne(id);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Persona persona = personaRepository.findPersonaByEmail(email);

        System.out.println("persona rol " + persona.getRol());

        if ((persona == null)) {

            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + email);

        } else {

            List<GrantedAuthority> permissions = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + persona.getRol().toString());

            permissions.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("userSession", persona);

            return new User(persona.getEmail(), persona.getPassword(), permissions);

        }

    }
    
}

