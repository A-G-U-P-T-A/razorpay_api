package com.example.razorpay.services;

import com.example.razorpay.configs.RazorpayConstants;
import com.example.razorpay.objects.CreateOrder;
import com.example.razorpay.objects.UpdateOrder;
import com.mongodb.MongoException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RazorpayService {

    RazorpayClient razorpayClient;

    @Autowired MongoService mongoService;

    RazorpayService() {
        try {
            razorpayClient = new RazorpayClient(RazorpayConstants.key, RazorpayConstants.secret);
        } catch (RazorpayException e) {
            System.out.println("Razorpay client failed to start stop application");
            System.out.println(e.getCause() + " " + e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object createOrder(CreateOrder createOrder) {
        JSONObject options = new JSONObject();
        options.put("amount", createOrder.getAmount());
        options.put("currency", createOrder.getCurrency());
        try {
            Order order = razorpayClient.Orders.create(options);
            mongoService.createEntry("orders", createOrderDocument(order));
            return ResponseEntity.status(HttpStatus.OK).body(order.toString());
        } catch (RazorpayException | MongoException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public Object updateOrder(UpdateOrder updateOrder) {
        try {
            mongoService.updateEntry("orders", updateOrder);
            return ResponseEntity.status(HttpStatus.OK).body("UPDATED");
        } catch (MongoException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public Object listAllOrders() {
        try {
            List<Order>orders = razorpayClient.Orders.fetchAll();
            List<String>orderIds = new ArrayList<>();
            for (Order order:orders) {
                orderIds.add(order.get("id"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(orderIds);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public Object getOrderAmountDue(String orderId) {
        try {
            Map<String, Object> orderMap = new HashMap<>();
            Integer amountDue = razorpayClient.Orders.fetch(orderId).get("amount_due");
            orderMap.put("amountDue", amountDue);
            return ResponseEntity.status(HttpStatus.OK).body(orderMap);
        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot Find Order Id: " + e.getMessage());
        }
    }

    private Document createOrderDocument(Order order) {
        Document document = new Document();
        document.append("orderId", order.get("id"));
        document.append("amount", order.get("id"));
        document.append("createdAt", order.get("created_at"));
        document.append("amountPaid", order.get("amount_paid"));
        document.append("status", order.get("status"));
        document.append("amountDue", order.get("amount_due"));
        return document;
    }
}
