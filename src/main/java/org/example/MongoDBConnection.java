//package org.example;
//
//import com.mongodb.client.*;
//import org.bson.Document;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MongoDBConnection {
//    private static final String CONNECTION_STRING = "mongodb+srv://tamimsharif2181:p5ZzWO1Z23dBlOsI@cluster0.mongodb.net/?retryWrites=true&w=majority";
//    private static final String DATABASE_NAME = "yourDatabaseName";
//    private static final String COLLECTION_NAME = "problemset";
//
//    private MongoClient mongoClient;
//    private MongoDatabase database;
//    private MongoCollection<Document> collection;
//
//    public MongoDBConnection() {
//        mongoClient = MongoClients.create(CONNECTION_STRING);
//        database = mongoClient.getDatabase(DATABASE_NAME);
//        collection = database.getCollection(COLLECTION_NAME);
//    }
//
//    public List<Document> getProblems() {
//        List<Document> problems = new ArrayList<>();
//        FindIterable<Document> docs = collection.find();  // Fetch all problems
//        for (Document doc : docs) {
//            problems.add(doc);
//        }
//        return problems;
//    }
//}
//
