package com.pokepedia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Configura y provee conexiones JDBC hacia MySQL.
 */
public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/pokepedia?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Retorna una conexión a la base de datos
     * @return Connection - conexión a la base de datos
     * @throws SQLException - si ocurre un error al obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

