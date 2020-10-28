package com.example.razorpay.services;

import com.example.razorpay.objects.UpdateOrder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

@Service
public class MongoService {

    public MongoClient mongoClient;

    MongoService() {
        try {
            mongoClient = MongoClients.create(
                    "mongodb+srv://newuser:newuser@cluster0.7l3am.mongodb.net/razorpay?retryWrites=true&w=majority");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEntry(String collectionName, Document entry) {
        mongoClient.getDatabase("razorpay").getCollection(collectionName).insertOne(entry);
    }

    public void updateEntry(String collectionName, UpdateOrder updateOrder) {
        Bson filter = Filters.eq("orderId", updateOrder.getOrderId());
        mongoClient.getDatabase("razorpay").getCollection(collectionName).updateOne(filter, new Document("$set", new Document("paymentId", updateOrder.getPaymentId())));
    }
}
