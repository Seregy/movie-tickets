package hall;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;

/**
 * Created by Incy on 08.03.2017.
 */
public class HallCodec implements Codec<Hall> {

    private CodecRegistry codecRegistry;

    public HallCodec(CodecRegistry codecRegistry)
    {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public Hall decode(final BsonReader bsonReader,
                          final DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        UUID id = UUID.fromString(bsonReader.readString("_id"));
        String name = bsonReader.readString("name");
        UUID cinemaId = UUID.fromString(bsonReader.readString("cinemaId"));

        bsonReader.readEndDocument();
        return new Hall(id, name, cinemaId);
    }

    @Override
    public void encode(final BsonWriter bsonWriter,
                       final Hall hall,
                       final EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", hall.getId().toString());
        bsonWriter.writeString("name", hall.getName());
        bsonWriter.writeString("cinemaId", hall.getCinemaId().toString());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Hall> getEncoderClass() {
        return Hall.class;
    }
}
