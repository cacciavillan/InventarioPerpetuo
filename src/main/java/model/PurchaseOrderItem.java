package model;

public class PurchaseOrderItem {
    private int id;
    private String type;
    private RawMaterial product;
    private int orderId;
    private int quantity;
    private double price;
    private double totalPrice;

    // Getters y setters

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public RawMaterial getProduct() {
        return product;
    }

    public void setProduct(RawMaterial product) {
        this.product = product;
    }

    // Método para calcular el valor total del item
    public void calculateTotalPrice() {
        this.totalPrice = this.price * this.quantity;
    }
}