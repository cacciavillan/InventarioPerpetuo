package model.repository;

import model.PurchaseOrder;

import java.sql.Connection;
import java.sql.SQLException;

public interface PurchaseOrderRepository {
    int save(PurchaseOrder order, Connection conn) throws SQLException;
    void update(PurchaseOrder order, Connection conn) throws SQLException;
}