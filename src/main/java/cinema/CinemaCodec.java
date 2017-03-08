package cinema;

import org.bson.BsonInt32;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * Custom codec for {@link Cinema} for storing {@link Cinema} in MongoDB.
 *
 * @author Seregy
 */
final class CinemaCodec implements CollectibleCodec<Cinema> {
    @Override
    public Cinema generateIdIfAbsentFromDocument(final Cinema cinema) {
        return cinema;
    }

    @Override
    public boolean documentHasId(final Cinema cinema) {
        return true;
    }

    @Override
    public BsonValue getDocumentId(final Cinema cinema) {
        return new BsonInt32(cinema.getId());
    }

    @Override
    public Cinema decode(final BsonReader bsonReader,
                         final DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        bsonReader.readObjectId("_id");
        int id = bsonReader.readInt32("id");
        String name = bsonReader.readString("name");
        String location = bsonReader.readString("location");
        bsonReader.readEndDocument();
        return new Cinema(id, name, location);
    }

    @Override
    public void encode(final BsonWriter bsonWriter,
                       final Cinema cinema,
                       final EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeInt32("id", cinema.getId());
        bsonWriter.writeString("name", cinema.getName());
        bsonWriter.writeString("location", cinema.getLocation());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Cinema> getEncoderClass() {
        return Cinema.class;
    }
}
