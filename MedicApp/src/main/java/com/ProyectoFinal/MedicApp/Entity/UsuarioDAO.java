package com.ProyectoFinal.MedicApp.Entity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsuarioDAO {
    private final String URL = "jdbc:mysql://localhost:3306/medicapp";
    private final String USUARIO = "root";
    private final String CONTRASENA = "root";

    public boolean validarCorreo(String correo) {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement stmt = conn.prepareStatement("SELECT email FROM paciente WHERE email = ?")) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true si el correo existe, false si no existe

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // en caso de error, asumimos que el correo no existe
    }
}