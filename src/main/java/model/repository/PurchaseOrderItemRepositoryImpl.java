package model.repository;

import model.PurchaseOrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseOrderItemRepositoryImpl implements PurchaseOrderItemRepository {
    @Override
    public int save(PurchaseOrderItem item, Connection conn) throws SQLException {
        String sql = "INSERT INTO purchase_order_items (type, quantity, price, order_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, item.getType());
            stmt.setInt(2, item.getQuantity());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getOrderId());
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
}