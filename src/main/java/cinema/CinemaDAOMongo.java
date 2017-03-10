package cinema;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import core.AbstractDAOMongo;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Data access object for {@link Cinema}, uses Mongo database.
 *
 * @author Seregy
 */
public final class CinemaDAOMongo extends AbstractDAOMongo<Cinema>
        implements CinemaDAO {
    private static final String COLLECTION = "cinema";

    /**
     * Constructs a new MongoDB DAO for {@link Cinema} objects
     * with default database's hostname, port and name.
     */
    public CinemaDAOMongo() {
        super(AbstractDAOMongo.HOSTNAME,
                AbstractDAOMongo.PORT,
                AbstractDAOMongo.DATABASE_NAME);
    }

    @Override
    public boolean update(final Cinema cinema) {
        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Cinema> collection = getCollection();

            BasicDBObject change = new BasicDBObject();
            change.put("name", cinema.getName());
            change.put("location", cinema.getLocation());

            UpdateResult updateResult = collection.updateOne(
                    Filters.eq("_id", cinema.getId().toString()),
                    new Document("$set", change));
            result = updateResult.wasAcknowledged();
        }

        return result;
    }

    @Override
    protected CodecRegistry getCustomCodecRegistry() {
        return CodecRegistries.fromCodecs(new CinemaCodec());
    }

    @Override
    protected MongoCollection<Cinema> getCollection() {
        return getMongoClient().getDatabase(getDatabaseName())
                .getCollection(COLLECTION, Cinema.class);
    }
}
