package util;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBConnector {

	public static final String USERS = "users";

	private static final String DATABASE_NAME = "hustlist";
	private static MongoClient connection = new MongoClient("localhost");
	private static MongoDatabase db = connection.getDatabase(DATABASE_NAME);
	
	public static MongoClient getConnection(){
		return connection;
	}
	
	public static MongoDatabase getDB(){
		return db;
	}
	
	public static void close(){
		connection.close();
	}
	public static FindIterable<Document> getUsers(Document searchQuery){
		return db.getCollection(USERS).find(searchQuery);
	}
	
}
