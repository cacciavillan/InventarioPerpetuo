package model;

import model.controller.PurchaseOrderController;
import model.dto.*;
import model.repository.*;
import model.service.*;
import model.util.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPurchaseOrderService {

    public static void main(String[] args) {
        try {
            // Configurar datos de prueba
            PurchaseOrderDTO orderDTO = new PurchaseOrderDTO();
            orderDTO.setDescription("Test Order");

            List<PurchaseOrderItemDTO> items = new ArrayList<>();
            PurchaseOrderItemDTO itemDTO = new PurchaseOrderItemDTO();
            itemDTO.setType("RawMaterial");
            itemDTO.setQuantity(10);
            itemDTO.setPrice(100.0);
            items.add(itemDTO);

            orderDTO.setItems(items);

            // Convertir DTO a JSON y luego a InputStream para simular la entrada
            String jsonOrder = "{ \"description\": \"Test Order\", \"items\": [ { \"type\": \"RawMaterial\", \"quantity\": 10, \"price\": 100.0 } ] }";
            InputStream inputStream = new ByteArrayInputStream(jsonOrder.getBytes());

            // Crear implementaciones ficticias de los repositorios
            PurchaseOrderRepository orderRepository = new PurchaseOrderRepositoryImpl();
            PurchaseOrderItemRepository itemRepository = new PurchaseOrderItemRepositoryImpl();
            ConnManager connectionManager = new TestConnManager();

            // Crear el servicio y el controlador
            PurchaseOrderService purchaseOrderService = new PurchaseOrderService(orderRepository, itemRepository, connectionManager);
            PurchaseOrderController controller = new PurchaseOrderController(purchaseOrderService);

            // Procesar la orden de compra
            String result = controller.processPurchaseOrder(inputStream);

            // Verificar el resultado
            System.out.println(result);
            if (result.contains("Orden de compra creada con ID:")) {
                System.out.println("Test passed");
            } else {
                System.out.println("Test failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed with exception: " + e.getMessage());
        }
    }
}

// Implementación de prueba para ConnManager
class TestConnManager extends ConnManager {
    private String url = "jdbc:postgresql://localhost:5432/test_db";
    private String user = "postgres";
    private String password = "machinasnk";

    public Connection getConnection() throws SQLException {
        // Registrar el controlador PostgreSQL
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL driver registered successfully.");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el controlador PostgreSQL", e);
        }
        // Obtener la conexión
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connection established.");
        return conn;
    }
}