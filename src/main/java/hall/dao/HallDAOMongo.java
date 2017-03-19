package hall.dao;

import core.dao.AbstractDAOMongo;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import hall.Hall;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by Incy on 09.03.2017.
 */
public final class HallDAOMongo extends AbstractDAOMongo<Hall> implements HallDAO {

    private static final String COLLECTION = "hall";

    public HallDAOMongo() {
        super(AbstractDAOMongo.HOSTNAME,
                AbstractDAOMongo.PORT,
                AbstractDAOMongo.DATABASE_NAME);
    }
    @Override
    public boolean update(Hall hall) {

        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Hall> collection = getCollection();

            BasicDBObject change = new BasicDBObject();
            change.put("name", hall.getName());
            change.put("cinemaId", hall.getCinemaId().toString());

            UpdateResult updateResult = collection.updateOne(
                    Filters.eq("_id", hall.getId().toString()),
                    new Document("$set", change));
            result = updateResult.wasAcknowledged();
        }

        return result;
    }

    @Override
    protected CodecRegistry getCustomCodecRegistry() {
        return CodecRegistries.fromCodecs(new HallCodec(MongoClient.getDefaultCodecRegistry()));
    }

    @Override
    protected MongoCollection<Hall> getCollection() {
        return getMongoClient()
                .getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION, Hall.class);
    }
}
