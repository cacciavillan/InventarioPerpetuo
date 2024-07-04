package model.factory;

import model.PurchaseOrderItem;
import model.dto.PurchaseOrderItemDTO;
import model.RawMaterial;

public class PurchaseOrderItemFactory {
    public static PurchaseOrderItem createPurchaseOrderItem(PurchaseOrderItemDTO itemData) {
        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setType(itemData.getType());
        item.setQuantity(itemData.getQuantity());
        item.setPrice(itemData.getPrice());

        if ("RawMaterial".equals(itemData.getType())) {
            RawMaterial product = new RawMaterial();
            item.setProduct(product);
        }

        return item;
    }
}