package model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnManager {
    private String url = "jdbc:postgresql://localhost:5432/test_db";
    private String user = "postgres";
    private String password = "machinasnk";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida exitosamente");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el controlador PostgreSQL", e);
        }
    }
}