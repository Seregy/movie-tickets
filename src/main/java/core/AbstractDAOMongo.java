package core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A basic implementation of most of the methods in DAO interface for MongoDB.
 *
 * @param <T> Object type used in dao
 * @author Seregy
 * @see DAO
 */
public abstract class AbstractDAOMongo<T>
        implements DAO<T> {
    /**
     * Default hostname for MongoDB.
     */
    public static final String HOSTNAME = "localhost";
    /**
     * Default port number for MongoDB.
     */
    public static final int PORT = 27017;
    /**
     * Default database name for MongoDB.
     */
    public static final String DATABASE_NAME = "test";

    private final ServerAddress serverAddress;
    private final String databaseName;

    /**
     * Constructs a new DAO object for MongoDB
     * with specified database's hostname, port and name.
     *
     * @param hostname host name of the database
     * @param port port number of the database
     * @param databaseName name of the database
     */
    protected AbstractDAOMongo(final String hostname,
                               final int port,
                               final String databaseName) {
        serverAddress = new ServerAddress(hostname, port);
        this.databaseName = databaseName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(final UUID id) {
        T object = null;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<T> collection = getCollection();
            try (MongoCursor<T> cursor =
                         collection.find(
                                 Filters.eq("_id",
                                 id.toString())
                         ).iterator()) {
                if (cursor.hasNext()) {
                    object = cursor.next();
                }
            }
        }

        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        List<T> objects = new ArrayList<>();
        try (MongoClient client = getMongoClient()) {
            MongoCollection<T> collection = getCollection();

            try (MongoCursor<T> cursor =
                         collection.find().iterator()) {
                while (cursor.hasNext()) {
                    objects.add(cursor.next());
                }
            }
        }

        return objects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(final T object) {
        try (MongoClient client = getMongoClient()) {
            MongoCollection<T> collection = getCollection();
            collection.insertOne(object);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean update(T object);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final UUID id) {
        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<T> collection = getCollection();

            DeleteResult deleteResult =
                    collection.deleteOne(Filters.eq("_id", id.toString()));
            result = deleteResult.wasAcknowledged();
        }

        return result;
    }

    /**
     * Returns {@link MongoClient}, connected to MongoDB.
     *
     * @return connected client
     */
    protected MongoClient getMongoClient() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        getCustomCodecRegistry(),
                        MongoClient.getDefaultCodecRegistry());
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
        return new MongoClient(serverAddress, options);
    }

    /**
     * Returns {@link CodecRegistry} that contains custom codecs.
     *
     * @return custom codec registry
     */
    protected abstract CodecRegistry getCustomCodecRegistry();

    /**
     * Returns {@link MongoCollection} of cinemas.
     *
     * @return collection of cinemas
     */
    protected abstract MongoCollection<T> getCollection();

    /**
     * Gets {@link ServerAddress} of the current database.
     *
     * @return address of the database
     */
    protected ServerAddress getServerAddress() {
        return serverAddress;
    }

    /**
     * Gets the name of the database.
     *
     * @return name of the database
     */
    protected String getDatabaseName() {
        return databaseName;
    }
}
