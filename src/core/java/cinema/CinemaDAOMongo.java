package core.java.cinema;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Seregy on 03.03.2017.
 */
public class CinemaDAOMongo implements CinemaDAO {
	private static final String URL = "localhost";
	private static final int PORT = 27017;
	private static final String USER = "root";
	private static final String PASSWORD = "pass";
	private static final String DATABASE = "db";
	private static final String COLLECTION = "cinema";

	private static MongoCredential credential = MongoCredential.createMongoCRCredential(USER,
			DATABASE,
			PASSWORD.toCharArray());
	private ServerAddress serverAddress = new ServerAddress(URL, PORT);

	@Override
	public Cinema find(int id) {
		Cinema cinema = null;

		MongoClient client = new MongoClient(serverAddress, Collections.singletonList(credential));
		MongoDatabase db = client.getDatabase(DATABASE);
		MongoCollection<Cinema> collection = db.getCollection(COLLECTION, Cinema.class);

		BasicDBObject filter = new BasicDBObject();
		filter.put("id", id);

		try (MongoCursor<Cinema> cursor = collection.find(filter).iterator()) {
			if (cursor.hasNext()) {
				cinema = cursor.next();
			}
		}

		return cinema;
	}

	@Override
	public List<Cinema> find(String name) {
		List<Cinema> cinemas = new ArrayList<>();
		MongoClient client = new MongoClient(serverAddress, Collections.singletonList(credential));
		MongoDatabase db = client.getDatabase(DATABASE);
		MongoCollection<Cinema> collection = db.getCollection(COLLECTION, Cinema.class);

		BasicDBObject filter = new BasicDBObject();
		filter.put("name", name);

		try (MongoCursor<Cinema> cursor = collection.find(filter).iterator()) {
			while (cursor.hasNext()) {
				cinemas.add(cursor.next());
			}
		}
		return cinemas;
	}

	@Override
	public List<Cinema> findAll() {
		List<Cinema> cinemas = new ArrayList<>();
		MongoClient client = new MongoClient(serverAddress, Collections.singletonList(credential));
		MongoDatabase db = client.getDatabase(DATABASE);
		MongoCollection<Cinema> collection = db.getCollection(COLLECTION, Cinema.class);

		try (MongoCursor<Cinema> cursor = collection.find().iterator()) {
			while (cursor.hasNext()) {
				cinemas.add(cursor.next());
			}
		}
		return cinemas;
	}

	@Override
	public boolean add(Cinema cinema) {
		boolean result = false;

		MongoClient client = new MongoClient(serverAddress, Collections.singletonList(credential));
		MongoDatabase db = client.getDatabase(DATABASE);
		MongoCollection<Cinema> collection = db.getCollection(COLLECTION, Cinema.class);
		collection.insertOne(cinema);
		result = true;

		return result;
	}

	@Override
	public boolean update(Cinema cinema) {
		boolean result = false;

		MongoClient client = new MongoClient(serverAddress, Collections.singletonList(credential));
		MongoDatabase db = client.getDatabase(DATABASE);
		MongoCollection<Cinema> collection = db.getCollection(COLLECTION, Cinema.class);

		BasicDBObject filter = new BasicDBObject();
		filter.put("id", cinema.getId());

		BasicDBObject change = new BasicDBObject();
		change.put("name", cinema.getName());
		change.put("location", cinema.getLocation());

		collection.updateOne(filter, change);
		result = true;

		return result;
	}

	@Override
	public boolean delete(Cinema cinema) {
		boolean result = false;

		MongoClient client = new MongoClient(serverAddress, Collections.singletonList(credential));
		MongoDatabase db = client.getDatabase(DATABASE);
		MongoCollection<Cinema> collection = db.getCollection(COLLECTION, Cinema.class);

		BasicDBObject filter = new BasicDBObject();
		filter.put("id", cinema.getId());

		collection.deleteOne(filter);
		result = true;

		return result;
	}
}
