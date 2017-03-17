package movie;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;

/**
 * Created by Incy on 06.03.2017.
 */
public class MovieCodec implements Codec<Movie> {
    private CodecRegistry codecRegistry;

    public MovieCodec(CodecRegistry codecRegistry)
    {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public Movie decode(final BsonReader bsonReader,
                         final DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        UUID id = UUID.fromString(bsonReader.readString("_id"));
        //Codec<UUID> uuidCodec = codecRegistry.get(UUID.class);
       // UUID id = uuidCodec.decode(bsonReader,decoderContext);
        String name = bsonReader.readString("name");
        int duration = bsonReader.readInt32("duration");
        String annotation = bsonReader.readString("annotation");
        bsonReader.readEndDocument();
        return new Movie(id, name, duration, annotation);
    }

    @Override
    public void encode(final BsonWriter bsonWriter,
                       final Movie movie,
                       final EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", movie.getId().toString());
        //bsonWriter.writeName("id");
       // Codec<UUID> uuidCodec = codecRegistry.get(UUID.class);
       // encoderContext.encodeWithChildContext(uuidCodec, bsonWriter, movie.getId());
        bsonWriter.writeString("name", movie.getName());
        bsonWriter.writeInt32("duration", movie.getDuration());
        bsonWriter.writeString("annotation", movie.getAnnotation());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Movie> getEncoderClass() {
        return Movie.class;
    }
}
