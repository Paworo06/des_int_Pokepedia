package com.pokepedia.dao;

import com.pokepedia.config.DatabaseConfig;
import com.pokepedia.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    /**
     * Retorna un objeto Usuario si se encuentra el usuario, de lo contrario retorna null
     * @param email - email del usuario
     * @param password - contraseña del usuario
     * @return Usuario - usuario encontrado
     * @throws SQLException - si ocurre un error al buscar el usuario
     */
    public Usuario buscarPorEmailYContrasena(String email, String password) {
        String sql = "SELECT id, nombre, email, password, rol FROM usuarios WHERE email = ? AND password = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

