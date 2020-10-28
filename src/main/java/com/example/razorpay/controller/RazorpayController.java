package com.example.razorpay.controller;

import com.example.razorpay.objects.CreateOrder;
import com.example.razorpay.objects.UpdateOrder;
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

    @CrossOrigin
    @PostMapping(value = "/createorder")
    public @ResponseBody Object createOrder(@RequestBody(required = false) CreateOrder createOrder) {
        List<String> validation = razorpayValidator.validate(createOrder);
        if(validation.size()!=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        return razorpayService.createOrder(createOrder);
    }

    @CrossOrigin
    @PostMapping(value = "/updateorder")
    public @ResponseBody Object updateOrder(@RequestBody(required = false) UpdateOrder updateOrder) {
        List<String> validation = razorpayValidator.validate(updateOrder);
        if(validation.size()!=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        return razorpayService.updateOrder(updateOrder);
    }

    @CrossOrigin
    @GetMapping(value = "/listorders")
    public @ResponseBody Object listOrders() {
        return razorpayService.listAllOrders();
    }

    @CrossOrigin
    @GetMapping(value = "/getorderamountdue")
    public @ResponseBody Object getOrderAmount(@RequestParam("id") String id) {
        List<String> validation = razorpayValidator.validate(id);
        if(validation.size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validation);
        }
        return razorpayService.getOrderAmountDue(id);
    }
}
