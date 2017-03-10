package user;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

/**
 * Custom codec for storing {@link User} objects in MongoDB.
 *
 * @author Seregy
 */
public final class UserCodec implements Codec<User> {
    @Override
    public User decode(final BsonReader reader,
                       final DecoderContext decoderContext) {
        reader.readStartDocument();
        reader.readObjectId("_id");
        UUID id = UUID.fromString(reader.readString("id"));
        String fullName = reader.readString("full_name");
        String userName = reader.readString("user_name");
        String password = reader.readString("password");
        String salt = reader.readString("salt");
        String email = reader.readString("email");
        reader.readEndDocument();
        return new User(id, fullName, userName, password, salt, email);
    }

    @Override
    public void encode(final BsonWriter writer,
                       final User user,
                       final EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("id", user.getId().toString());
        writer.writeString("full_name", user.getFullName());
        writer.writeString("user_name", user.getUserName());
        writer.writeString("password", user.getPassword());
        writer.writeString("salt", user.getSalt());
        writer.writeString("email", user.getEmail());
        writer.writeEndDocument();

    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }
}
