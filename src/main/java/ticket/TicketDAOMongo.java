package ticket;

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
 * Data access object for {@link Ticket}, uses Mongo database.
 *
 * @author Seregy
 */
public final class TicketDAOMongo extends AbstractDAOMongo<Ticket>
        implements TicketDAO {
    private static final String COLLECTION = "ticket";

    /**
     * Constructs a new MongoDB DAO for {@link Ticket} objects
     * with default database's hostname, port and name.
     */
    public TicketDAOMongo() {
        super(AbstractDAOMongo.HOSTNAME,
                AbstractDAOMongo.PORT,
                AbstractDAOMongo.DATABASE_NAME);
    }

    @Override
    public boolean update(final Ticket ticket) {
        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Ticket> collection = getCollection();

            BasicDBObject change = new BasicDBObject();
            change.put("user_id", ticket.getUserId().toString());
            change.put("row", ticket.getRow());
            change.put("seat", ticket.getSeat());

            UpdateResult updateResult = collection.updateOne(
                    Filters.eq("_id", ticket.getId().toString()),
                    new Document("$set", change));
            result = updateResult.wasAcknowledged();
        }

        return result;
    }

    @Override
    protected CodecRegistry getCustomCodecRegistry() {
        return CodecRegistries.fromCodecs(new TicketCodec());
    }

    @Override
    protected MongoCollection<Ticket> getCollection() {
        return getMongoClient().getDatabase(getDatabaseName())
                .getCollection(COLLECTION, Ticket.class);
    }
}
