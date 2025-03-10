package org.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class DatabaseController {
    // Store connection details in environment variables or a properties file in practice
    private static final String CONNECTION_STRING = "mongodb+srv://tamimsharif2181:p5ZzWO1Z23dBlOsI@cluster0.gukwh.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "brainstrom";
    private static final String COLLECTION_NAME = "users";

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> usersCollection;

    static {
        try {
            // Use the connection string directly (no replacement needed)
            MongoClientURI uri = new MongoClientURI(CONNECTION_STRING);
            mongoClient = new MongoClient(uri);
            database = mongoClient.getDatabase(DATABASE_NAME);
            usersCollection = database.getCollection(COLLECTION_NAME);
            System.out.println("MongoDB connection established successfully");
        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add a new user to the database
    public static boolean registerUser(String username, String password) {
        try {
            // Check if username already exists
            Document existingUser = usersCollection.find(Filters.eq("username", username)).first();
            if (existingUser != null) {
                System.out.println("Username already exists");
                return false;
            }

            // Create new user document
            Document newUser = new Document("username", username)
                    .append("password", password);  // Consider hashing passwords!

            // Insert the document
            usersCollection.insertOne(newUser);
            System.out.println("User registered successfully");
            return true;
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Verify user credentials
    public static boolean verifyUser(String username, String password) {
        try {
            Document user = usersCollection.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("password", password)  // Consider hashing passwords!
            )).first();

            return user != null;
        } catch (Exception e) {
            System.err.println("Error verifying user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Close the MongoDB connection
    public static void closeConnection() {
        try {
            if (mongoClient != null) {
                mongoClient.close();
                System.out.println("MongoDB connection closed");
            }
        } catch (Exception e) {
            System.err.println("Error closing MongoDB connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}