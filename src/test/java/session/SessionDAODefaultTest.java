package session;

import session.dao.SessionDAO;
import session.dao.SessionDAODefault;
import session.dao.SessionDAOMongo;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Incy on 16.03.2017.
 */
public class SessionDAODefaultTest {

    private final Session[] sessions = {new Session(LocalDateTime.now(), UUID.randomUUID(),UUID.randomUUID()),
            new Session(LocalDateTime.now(), UUID.randomUUID(),UUID.randomUUID()),
            new Session(LocalDateTime.now(), UUID.randomUUID(),UUID.randomUUID())};

    private final SessionDAOMongo sessionDAOMongo = new SessionDAOMongo();
    private final SessionDAODefault sessionDAODefault = new SessionDAODefault();


    @org.junit.Test
    public void testDefault() throws Exception {
        add(sessionDAODefault);
        find(sessionDAODefault);
        update(sessionDAODefault);
        findAll(sessionDAODefault);
        delete(sessionDAODefault);
    }

    @org.junit.Test
    public void testMongo() throws Exception {
        add(sessionDAOMongo);
        find(sessionDAOMongo);
       // findAll(sessionDAOMongo);
       // update(sessionDAOMongo);
       // delete(sessionDAOMongo);
    }



    private void add(SessionDAO sessionDAO)  {
        for (Session session: sessions)
            sessionDAO.add(session);
    }
    private void find(SessionDAO sessionDAO)
    {
        assertEquals(sessions[0],sessionDAO.find(sessions[0].getId()));
    }

    private void findAll(SessionDAO sessionDAO)
    {
        assertEquals(sessions.length,sessionDAO.findAll().size());
    }

    private void update(SessionDAO sessionDAO)
    {
        sessions[2].setHallId(UUID.randomUUID());
        sessionDAO.update(sessions[2]);
        assertEquals(sessions[2].getHallId(),sessionDAO.find(sessions[2].getId()).getHallId());
    }


    private void delete(SessionDAO sessionDAO) {
        for (Session session: sessions)
        {
            sessionDAO.delete(session.getId());
        }
        assertNull(sessionDAO.find(sessions[1].getId()));
    }


}