package cinema;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * Custom codec for storing {@link Cinema} objects in MongoDB.
 *
 * @author Seregy
 */
final class CinemaCodec implements Codec<Cinema> {

    @Override
    public Cinema decode(final BsonReader reader,
                         final DecoderContext decoderContext) {
        reader.readStartDocument();
        reader.readObjectId("_id");
        int id = reader.readInt32("id");
        String name = reader.readString("name");
        String location = reader.readString("location");
        reader.readEndDocument();
        return new Cinema(id, name, location);
    }

    @Override
    public void encode(final BsonWriter writer,
                       final Cinema cinema,
                       final EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeInt32("id", cinema.getId());
        writer.writeString("name", cinema.getName());
        writer.writeString("location", cinema.getLocation());
        writer.writeEndDocument();
    }

    @Override
    public Class<Cinema> getEncoderClass() {
        return Cinema.class;
    }
}
