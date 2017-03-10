package ticket;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import java.util.UUID;

/**
 * Custom codec for storing {@link Ticket} objects in MongoDB.
 *
 * @author Seregy
 */
public final class TicketCodec implements Codec<Ticket> {
    @Override
    public Ticket decode(final BsonReader reader,
                         final DecoderContext decoderContext) {
        reader.readStartDocument();
        UUID id = UUID.fromString(reader.readString("_id"));
        UUID userId = UUID.fromString(reader.readString("user_id"));
        int row = reader.readInt32("row");
        int seat = reader.readInt32("seat");
        reader.readEndDocument();
        return new Ticket(id, userId, row, seat);
    }

    @Override
    public void encode(final BsonWriter writer,
                       final Ticket ticket,
                       final EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("_id", ticket.getId().toString());
        writer.writeString("user_id", ticket.getUserId().toString());
        writer.writeInt32("row", ticket.getRow());
        writer.writeInt32("seat", ticket.getSeat());
        writer.writeEndDocument();
    }

    @Override
    public Class<Ticket> getEncoderClass() {
        return Ticket.class;
    }
}
