package com.example.razorpay.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrder {
    public String orderId;
    public String paymentId;
}
