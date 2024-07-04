package model.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.PurchaseOrder;
import model.PurchaseOrderItem;
import model.util.JsonUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final JsonUtil jsonUtil = new JsonUtil();

    @Override
    public int save(PurchaseOrder order, Connection conn) throws SQLException {
        String sql = "INSERT INTO purchase_orders (date, supplier, items, total_value) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, java.sql.Date.valueOf(order.getDate()));
            stmt.setString(2, order.getSupplier());
            stmt.setString(3, convertItemsToJson(order.getItems()));
            stmt.setDouble(4, order.getTotalValue());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // return the generated ID
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public void update(PurchaseOrder order, Connection conn) throws SQLException {
        String sql = "UPDATE purchase_orders SET date = ?, supplier = ?, items = ?, total_value = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(order.getDate()));
            stmt.setString(2, order.getSupplier());
            stmt.setString(3, convertItemsToJson(order.getItems()));
            stmt.setDouble(4, order.getTotalValue());
            stmt.setInt(5, order.getId());
            stmt.executeUpdate();
        }
    }

    private String convertItemsToJson(List<PurchaseOrderItem> items) {
        try {
            return jsonUtil.serialize(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir los items a JSON", e);
        }
    }
}