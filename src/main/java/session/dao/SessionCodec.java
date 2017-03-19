package session.dao;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import session.Session;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Incy on 07.03.2017.
 */
public class SessionCodec implements Codec<Session> {

    private CodecRegistry codecRegistry;

    public SessionCodec(CodecRegistry codecRegistry)
    {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public Session decode(final BsonReader bsonReader,
                        final DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        UUID id = UUID.fromString(bsonReader.readString("_id"));
        String date = bsonReader.readString("sessionStart");
        LocalDateTime sessionStart = LocalDateTime.parse(date);
        UUID movieId = UUID.fromString(bsonReader.readString("movieId"));
        UUID hallId = UUID.fromString(bsonReader.readString("hallId"));
        bsonReader.readEndDocument();
        return new Session(id, sessionStart, movieId,hallId);
    }

    @Override
    public void encode(final BsonWriter bsonWriter,
                       final Session session,
                       final EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", session.getId().toString());
        bsonWriter.writeString("sessionStart",session.getSessionStart().toString());
        bsonWriter.writeString("movieId", session.getMovieId().toString());
        bsonWriter.writeString("hallId", session.getHallId().toString());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Session> getEncoderClass() {
        return Session.class;
    }


}
