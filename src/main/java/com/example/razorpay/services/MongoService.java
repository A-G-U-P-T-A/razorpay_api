package com.example.razorpay.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class MongoService {

    public MongoClient mongoClient;

    MongoService() {
         mongoClient = MongoClients.create(
                "mongodb+srv://newuser:newuser@cluster0.7l3am.mongodb.net/razorpay?retryWrites=true&w=majority");
    }

    public void createEntry(String collectionName, Document entry) {
        mongoClient.getDatabase("razorpay").getCollection(collectionName).insertOne(entry);
    }
}
