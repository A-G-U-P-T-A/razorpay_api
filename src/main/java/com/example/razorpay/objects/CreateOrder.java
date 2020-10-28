package com.example.razorpay.objects;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class CreateOrder {
    public int amount = 0;
    public String currency;
}
