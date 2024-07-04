package model.repository;

import model.PurchaseOrderItem;

import java.sql.Connection;
import java.sql.SQLException;

public interface PurchaseOrderItemRepository {
    int save(PurchaseOrderItem item, Connection conn) throws SQLException;
}