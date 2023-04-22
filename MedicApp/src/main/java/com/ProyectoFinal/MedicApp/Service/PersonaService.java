//package com.ProyectoFinal.MedicApp.Service;
//
//import com.ProyectoFinal.MedicApp.Entity.Persona;
//import com.ProyectoFinal.MedicApp.Exception.MiExcepcion;
//import com.ProyectoFinal.MedicApp.Repository.PersonaRepositorio;
//import java.util.ArrayList;
//import java.util.List;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.transaction.Transactional;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Service
//public class PersonaService implements UserDetailsService {
//
//    @Autowired
//    private PersonaRepositorio personaRepositorio;
//
//    @Transactional
//
//    public void crearPersona(String nombre, String apellido, String email, String telefono, String password) throws MiExcepcion {
//
//
//
//        validarPersona(nombre, apellido,email, telefono, password);
//        
//        Persona persona = new Persona();
//        persona.setApellido(apellido);
//        persona.setEmail(email);
//        persona.setTelefono(telefono);
//        persona.setPassword(new BCryptPasswordEncoder().encode(password));;
//
//        personaRepositorio.save(persona);
//
//    }
//
//    public void validarPersona(String nombre, String apellido, String email, String telefono, String password) throws MiExcepcion {
//        try {
//            if (nombre == null || nombre.isEmpty()) {
//                throw new MiExcepcion("El nombre de la persona no puede ser nulo o vacío");
//            }
//
//            if (apellido == null || apellido.isEmpty()) {
//                throw new MiExcepcion("El apellido de la persona no puede ser nulo o vacío");
//            }
//
//            if (email == null || email.isEmpty()) {
//                throw new MiExcepcion("El correo electrónico de la persona no puede ser nulo o vacío");
//            }
//
//            if (telefono == null || telefono.isEmpty()) {
//                throw new MiExcepcion("El teléfono de la persona no puede ser nulo o vacío");
//            }
//
//            if (password == null || password.isEmpty()) {
//                throw new MiExcepcion("La contraseña de la persona no puede ser nula o vacía");
//            }
//
//        } catch (MiExcepcion ex) {
//            throw ex;
//        }
//    }
//    
//        @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        
//        Persona persona = personaRepositorio.buscarPorEmail(email);
//           
//
//        if (persona != null) {
//
//            List<GrantedAuthority> permisos = new ArrayList();
//
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + persona.getRol().toString());
//
//            permisos.add(p);
//
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//
//            HttpSession session = attr.getRequest().getSession(true);
//
//            session.setAttribute("usuariosession", persona);
//
//            return new User(persona.getEmail(), persona.getPassword(), permisos);
//        } else {
//            return null;
//        }
//
//    }
//}
