package user.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import core.dao.AbstractDAOMongo;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import user.User;

/**
 * Data access object for {@link User}, uses Mongo database.
 *
 * @author Seregy
 */
public final class UserDAOMongo extends AbstractDAOMongo<User>
        implements UserDAO {
    private static final String COLLECTION = "user";

    /**
     * Constructs a new MongoDB DAO for {@link User} objects
     * with default database's hostname, port and name.
     */
    public UserDAOMongo() {
        super(AbstractDAOMongo.HOSTNAME,
                AbstractDAOMongo.PORT,
                AbstractDAOMongo.DATABASE_NAME);
    }

    @Override
    public boolean update(final User user) {
        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<User> collection = getCollection();

            BasicDBObject change = new BasicDBObject();
            change.put("full_name", user.getFullName());
            change.put("user_name", user.getUserName());
            change.put("password", user.getPassword());
            change.put("salt", user.getSalt());
            change.put("email", user.getEmail());

            UpdateResult updateResult = collection.updateOne(
                    Filters.eq("_id", user.getId().toString()),
                    new Document("$set", change));
            result = updateResult.wasAcknowledged();
        }

        return result;
    }

    @Override
    protected CodecRegistry getCustomCodecRegistry() {
        return CodecRegistries.fromCodecs(new UserCodec());
    }

    @Override
    protected MongoCollection<User> getCollection() {
        return getMongoClient().getDatabase(getDatabaseName())
                .getCollection(COLLECTION, User.class);
    }
}
