package movie.dao;

import core.dao.AbstractDAOMongo;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import movie.Movie;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Incy on 06.03.2017.
 */
public final class MovieDAOMongo extends AbstractDAOMongo<Movie> implements MovieDAO {

    private static final String COLLECTION = "movie";

    public MovieDAOMongo() {
        super(AbstractDAOMongo.HOSTNAME,
                AbstractDAOMongo.PORT,
                AbstractDAOMongo.DATABASE_NAME);
    }

    @Override
    public boolean update(final Movie movie) {
        boolean result;

        try (MongoClient client = getMongoClient()) {
            MongoCollection<Movie> collection = getCollection();

            BasicDBObject change = new BasicDBObject();
            change.put("name", movie.getName());
            change.put("duration", movie.getDuration());
            change.put("annotation", movie.getAnnotation());

            UpdateResult updateResult = collection.updateOne(
                    Filters.eq("_id", movie.getId().toString()),
                    new Document("$set", change));
            result = updateResult.wasAcknowledged();
        }

        return result;
    }

    @Override
    protected CodecRegistry getCustomCodecRegistry() {
        return CodecRegistries
                .fromCodecs(new MovieCodec(MongoClient
                        .getDefaultCodecRegistry()));
    }

    @Override
    protected MongoCollection<Movie> getCollection() {
        return getMongoClient().getDatabase(getDatabaseName())
                .getCollection(COLLECTION, Movie.class);
    }
}