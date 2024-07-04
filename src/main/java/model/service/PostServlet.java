package model.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.controller.PurchaseOrderController;
import model.repository.PurchaseOrderItemRepository;
import model.repository.PurchaseOrderItemRepositoryImpl;
import model.repository.PurchaseOrderRepository;
import model.repository.PurchaseOrderRepositoryImpl;
import model.util.ConnManager;

import java.io.*;

public class PostServlet extends HttpServlet {
    private PurchaseOrderService purchaseOrderService;

    @Override
    public void init() throws ServletException {
        PurchaseOrderRepository orderRepository = new PurchaseOrderRepositoryImpl();
        PurchaseOrderItemRepository itemRepository = new PurchaseOrderItemRepositoryImpl();
        ConnManager connectionManager = new ConnManager();
        purchaseOrderService = new PurchaseOrderService(orderRepository, itemRepository, connectionManager);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PurchaseOrderController controller = new PurchaseOrderController(purchaseOrderService);
        String result = controller.processPurchaseOrder(request.getInputStream());

        response.setContentType("text/plain");
        response.getWriter().println(result);
    }
}