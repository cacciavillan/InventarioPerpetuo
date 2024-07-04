package model;

import java.sql.*;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/test_db";
        String user = "postgres";
        String password = "machinasnk";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Conexión establecida exitosamente");

                // Prueba de inserción
                String sql = "INSERT INTO purchase_orders (description) VALUES ('Test Order')";
                try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        try (ResultSet rs = stmt.getGeneratedKeys()) {
                            if (rs.next()) {
                                System.out.println("Insert successful, ID: " + rs.getInt(1));
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el controlador PostgreSQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Fallo en la conexión a la base de datos");
            e.printStackTrace();
        }
    }
}
