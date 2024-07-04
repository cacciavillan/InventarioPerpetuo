package model.service;

import model.*;
import model.dto.*;
import model.factory.PurchaseOrderItemFactory;
import model.repository.PurchaseOrderItemRepository;
import model.repository.PurchaseOrderRepository;
import model.util.ConnManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrderService {
    private final PurchaseOrderRepository orderRepository;
    private final PurchaseOrderItemRepository itemRepository;
    private final ConnManager connectionManager;

    public PurchaseOrderService(PurchaseOrderRepository orderRepository,
                                PurchaseOrderItemRepository itemRepository,
                                ConnManager connectionManager) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.connectionManager = connectionManager;
    }

    private PurchaseOrderItem processAndSaveItem(PurchaseOrderItemDTO itemDTO, int orderId, Connection conn) {
        try {
            PurchaseOrderItem item = PurchaseOrderItemFactory.createPurchaseOrderItem(itemDTO);
            item.setOrderId(orderId);
            itemRepository.save(item, conn);
            return item;
        } catch (SQLException e) {
            // Relanzar la excepción como RuntimeException
            throw new RuntimeException("Error al guardar el item de la orden", e);
        }
    }

    private List<PurchaseOrderItem> processItems(List<PurchaseOrderItemDTO> itemDTOs, int orderId, Connection conn) {
        return itemDTOs.stream()
                .map(itemDTO -> processAndSaveItem(itemDTO, orderId, conn))
                .collect(Collectors.toList());
    }

    public String processPurchaseOrder(PurchaseOrderDTO orderData) {
        try (Connection conn = connectionManager.getConnection()) {
            conn.setAutoCommit(false);

            // Crear y configurar la nueva orden de compra
            PurchaseOrder order = new PurchaseOrder();
            order.setDate(LocalDate.now(ZoneId.of("AGT", ZoneId.SHORT_IDS)));
            order.setSupplier(orderData.getSupplier());

            // Guardar la orden y obtener el ID generado
            int orderId = orderRepository.save(order, conn);
            order.setId(orderId);

            // Procesar y guardar los items de la orden
            List<PurchaseOrderItem> items = processItems(orderData.getItems(), orderId, conn);

            // Actualizar la orden con los items y calcular el valor total
            order.setItems(items);
            order.calculateTotalValue();
            orderRepository.update(order, conn);  // Actualiza el valor total en la base de datos

            // Confirmar la transacción
            conn.commit();
            return "Orden de compra creada con ID: " + orderId;
        } catch (SQLException e) {
            return "Error al procesar la orden: " + e.getMessage();
        }
    }
}