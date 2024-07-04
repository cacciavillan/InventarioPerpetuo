package model;

import java.time.LocalDate;
import java.util.List;

public class PurchaseOrder {
    private int id;
    private LocalDate date;
    private String supplier;
    private List<PurchaseOrderItem> items;
    private double totalValue; // Campo para almacenar el valor total

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<PurchaseOrderItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseOrderItem> items) {
        this.items = items;
        this.calculateTotalValue(); // Calcular el valor total cuando se establezcan los items
    }

    public double getTotalValue() {
        return totalValue;
    }

    private void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    // Método para calcular el valor total de la orden
    public void calculateTotalValue() {
        this.totalValue = 0;
        for (PurchaseOrderItem item : items) {
            item.calculateTotalPrice(); // Asegurarse de que cada item tenga su total calculado
            this.totalValue += item.getTotalPrice();
        }
    }
}