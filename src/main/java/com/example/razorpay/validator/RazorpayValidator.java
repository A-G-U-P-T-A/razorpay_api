package com.example.razorpay.validator;

import com.example.razorpay.objects.CreateOrder;
import com.example.razorpay.objects.UpdateOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RazorpayValidator {

    public List<String> validate(CreateOrder createOrder) {
        List<String> errorList = new ArrayList<String>();
        if(createOrder == null) {
            errorList.add("No Body Passed in the request. To create new order please pass a new object");
            return errorList;
        }
        if(createOrder.getAmount() == 0)
            errorList.add("Order Amount is Invalid or not provided");
        if(createOrder.getCurrency() == null || createOrder.getCurrency().trim().equals(""))
            errorList.add("Order Currency is Invalid or not provided");
        return errorList;
    }

    public List<String> validate(UpdateOrder updateOrder) {
        List<String> errorList = new ArrayList<String>();
        if(updateOrder == null) {
            errorList.add("No Body Passed in the request. To create new order please pass a new object");
            return errorList;
        }
        if(updateOrder.getOrderId() == null || updateOrder.getOrderId().trim().equals(""))
            errorList.add("Order order id is Invalid or not provided");
        if(updateOrder.getPaymentId() == null || updateOrder.getPaymentId().trim().equals(""))
            errorList.add("Order payment id is Invalid or not provided");
        return errorList;
    }

    public List<String> validate(String id) {
        List<String> errorList = new ArrayList<String>();
        if(id==null||id.trim().equals("")) {
            errorList.add("Id cannot be null please pass an id field along with the request");
        }
        return errorList;
    }
}
