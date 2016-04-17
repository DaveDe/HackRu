package com.example.david.hackathon;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.util.Arrays.asList;

/**
 * Created by mouth on 4/17/16.
 */
public class MongoDriver {

    MongoClient mongoClient = new MongoClient();
    MongoDatabase db = mongoClient.getDatabase("test");

    

}
