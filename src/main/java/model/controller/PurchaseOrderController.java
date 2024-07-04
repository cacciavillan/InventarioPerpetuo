package model.controller;

import model.dto.PurchaseOrderDTO;
import model.service.PurchaseOrderService;
import model.util.JsonDeserializer;
import java.io.*;

public class PurchaseOrderController {
    private JsonDeserializer jsonDeserializer;
    private PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.jsonDeserializer = new JsonDeserializer();
        this.purchaseOrderService = purchaseOrderService;
    }

    public String processPurchaseOrder(InputStream inputStream) throws IOException {
        PurchaseOrderDTO dto = jsonDeserializer.deserialize(inputStream, PurchaseOrderDTO.class);
        return purchaseOrderService.processPurchaseOrder(dto);
    }
}