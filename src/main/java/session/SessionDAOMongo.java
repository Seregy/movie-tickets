package session;

import core.AbstractDAOMongo;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by Incy on 09.03.2017.
 */
public final class SessionDAOMongo extends AbstractDAOMongo<Session> implements SessionDAO {

    private static final String COLLECTION = "session";

    public SessionDAOMongo() {
        super(AbstractDAOMongo.HOSTNAME,
                AbstractDAOMongo.PORT,
                AbstractDAOMongo.DATABASE_NAME);
    }
    @Override
    public boolean update(Session session) {

        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Session> collection = getCollection();

            BasicDBObject change = new BasicDBObject();
            change.put("sessionStart", session.getSessionStart().toString());
            change.put("movieId", session.getMovieId().toString());
            change.put("hallId", session.getHallId().toString());

            UpdateResult updateResult = collection.updateOne(
                    Filters.eq("_id", session.getId().toString()),
                    new Document("$set", change));
            result = updateResult.wasAcknowledged();
        }

        return result;
    }

    @Override
    protected CodecRegistry getCustomCodecRegistry() {
        return CodecRegistries.fromCodecs(new SessionCodec(MongoClient.getDefaultCodecRegistry()));
    }

    @Override
    protected MongoCollection<Session> getCollection() {
        return getMongoClient()
                .getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION, Session.class);
    }
}
