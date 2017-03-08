package cinema;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for {@link Cinema}, uses Mongo database.
 *
 * @author Seregy
 */
public final class CinemaDAOMongo implements CinemaDAO {
    private static final String URL = "localhost";
    private static final int PORT = 27017;
    private static final String DATABASE = "test";
    private static final String COLLECTION = "cinema";

    private static final ServerAddress SERVER_ADDRESS =
            new ServerAddress(URL, PORT);

    @Override
    public Cinema find(final int id) {
        Cinema cinema = null;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Cinema> collection =
                    getCinemaMongoCollection(client);

            BasicDBObject filter = new BasicDBObject();
            filter.put("id", id);

            try (MongoCursor<Cinema> cursor =
                         collection.find(filter).iterator()) {
                if (cursor.hasNext()) {
                    cinema = cursor.next();
                }
            }
        }

        return cinema;
    }

    @Override
    public List<Cinema> find(final String name) {
        List<Cinema> cinemas = new ArrayList<>();

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Cinema> collection =
                    getCinemaMongoCollection(client);

            BasicDBObject filter = new BasicDBObject();
            filter.put("name", name);

            try (MongoCursor<Cinema> cursor =
                         collection.find(filter).iterator()) {
                while (cursor.hasNext()) {
                    cinemas.add(cursor.next());
                }
            }
        }

        return cinemas;
    }

    @Override
    public List<Cinema> findAll() {
        List<Cinema> cinemas = new ArrayList<>();
        try (MongoClient client = getMongoClient()) {
            MongoCollection<Cinema> collection =
                    getCinemaMongoCollection(client);

            try (MongoCursor<Cinema> cursor =
                         collection.find().iterator()) {
                while (cursor.hasNext()) {
                    cinemas.add(cursor.next());
                }
            }
        }

        return cinemas;
    }

    @Override
    public boolean add(final Cinema cinema) {
        try (MongoClient client = getMongoClient()) {
            MongoCollection<Cinema> collection =
                    getCinemaMongoCollection(client);
            collection.insertOne(cinema);
        }

        return true;
    }

    @Override
    public boolean update(final Cinema cinema) {
        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Cinema> collection =
                    getCinemaMongoCollection(client);

            BasicDBObject change = new BasicDBObject();
            change.put("name", cinema.getName());
            change.put("location", cinema.getLocation());

            UpdateResult updateResult = collection.updateOne(
                    Filters.eq("id", cinema.getId()),
                    new Document("$set", change));
            result = updateResult.wasAcknowledged();
        }

        return result;
    }

    @Override
    public boolean delete(final Cinema cinema) {
        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Cinema> collection =
                    getCinemaMongoCollection(client);

            BasicDBObject filter = new BasicDBObject();
            filter.put("id", cinema.getId());

            DeleteResult deleteResult = collection.deleteOne(filter);
            result = deleteResult.wasAcknowledged();
        }

        return result;
    }

    /**
     * Returns {@link MongoClient}, connected to MongoDB.
     *
     * @return connected client
     */
    private MongoClient getMongoClient() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new CinemaCodec()),
                MongoClient.getDefaultCodecRegistry());
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
        return new MongoClient(SERVER_ADDRESS, options);
    }

    /**
     * Returns {@link MongoCollection} of cinemas.
     *
     * @param client {@link MongoClient}, connected to the database
     * @return collection of cinemas
     */
    private MongoCollection<Cinema> getCinemaMongoCollection(
            final MongoClient client) {
        MongoDatabase db = client.getDatabase(DATABASE);
        return db.getCollection(COLLECTION, Cinema.class);
    }
}
