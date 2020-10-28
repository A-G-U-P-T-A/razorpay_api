package com.example.razorpay.controller;

import com.example.razorpay.objects.CreateOrder;
import com.example.razorpay.services.RazorpayService;
import com.example.razorpay.validator.RazorpayValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RazorpayController {

    @Autowired
    RazorpayService razorpayService;

    @Autowired
    RazorpayValidator razorpayValidator;

    @PostMapping(value = "/createorder")
    public Object createOrder(@RequestBody(required = false) CreateOrder createOrder) {
        List<String> validation = razorpayValidator.validate(createOrder);
        if(validation != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        return razorpayService.createOrder(createOrder);
    }

    @CrossOrigin
    @GetMapping(value = "/listorders")
    public Object listOrders() {
        return razorpayService.listAllOrders();
    }
}
