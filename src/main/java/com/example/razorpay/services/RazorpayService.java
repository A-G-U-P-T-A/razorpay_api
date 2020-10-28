package com.example.razorpay.services;

import com.example.razorpay.configs.RazorpayConstants;
import com.example.razorpay.objects.CreateOrder;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            Document document = new Document();
            document.append("orderId", order.get("id"));
            mongoService.CreateEntry("orders", document);
            return ResponseEntity.status(HttpStatus.OK).body(order.toString());
        } catch (RazorpayException e) {
            e.printStackTrace();
            return e.getCause();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause();
        }
    }
}