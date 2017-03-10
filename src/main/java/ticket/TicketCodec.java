package ticket;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import user.User;

import java.util.UUID;

/**
 * Custom codec for storing {@link Ticket} objects in MongoDB.
 *
 * @author Seregy
 */
public final class TicketCodec implements Codec<Ticket> {
    private final CodecRegistry codecRegistry;

    /**
     * Creates a new instance of {@link TicketCodec} that will
     * use given {@link CodecRegistry} for decoding its values.
     *
     * @param codecRegistry contains codecs for
     *                      encoding/decoding {@code Ticket} values
     */
    public TicketCodec(final CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public Ticket decode(final BsonReader reader,
                         final DecoderContext decoderContext) {
        Codec<User> userCodec = codecRegistry.get(User.class);
        reader.readStartDocument();
        UUID id = UUID.fromString(reader.readString("id"));
        reader.readStartDocument();
        User user = userCodec.decode(reader, decoderContext);
        reader.readEndDocument();
        int row = reader.readInt32("row");
        int seat = reader.readInt32("seat");
        reader.readEndDocument();
        return new Ticket(id, user, row, seat);
    }

    @Override
    public void encode(final BsonWriter writer,
                       final Ticket ticket,
                       final EncoderContext encoderContext) {
        writer.writeStartDocument();
        Codec<User> userCodec = codecRegistry.get(User.class);
        writer.writeString("id", ticket.getId().toString());
        userCodec.encode(writer, ticket.getUser(), encoderContext);
        writer.writeInt32("row", ticket.getRow());
        writer.writeInt32("seat", ticket.getSeat());
        writer.writeEndDocument();
    }

    @Override
    public Class<Ticket> getEncoderClass() {
        return Ticket.class;
    }
}
