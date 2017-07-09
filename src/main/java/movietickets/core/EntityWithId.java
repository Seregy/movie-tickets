package movietickets.core;

import java.util.UUID;

/**
 * Entity that has unique identifier.
 *
 * @author Seregy
 */
public interface EntityWithId {
    /**
     * Gets unique identifier of the entity.
     *
     * @return unique identifier
     */
    UUID getId();
}
