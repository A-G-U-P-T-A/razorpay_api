package com.example.razorpay.validator;

import com.example.razorpay.objects.CreateOrder;
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
        if(errorList.size()!=0)
            return errorList;
        return null;
    }
}
