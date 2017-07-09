package movietickets.session.dao;

import movietickets.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Tests for Session data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.DAOTestConfiguration.class)
@Transactional
public class SessionDAOTest {
    @Autowired
    private SessionDAO sessionDAO;

    @Test
    public void findSession() {
        Session[] sessions = {
                new Session(LocalDateTime.of(2017, 7, 7, 10, 0)),
                new Session(LocalDateTime.of(2017, 7, 8, 12, 20)),
                new Session(LocalDateTime.of(2017, 7, 9, 14, 15))};
        for (Session session : sessions) {
            sessionDAO.add(session);
        }

        assertEquals(sessions[1], sessionDAO.find(sessions[1].getId()));
    }

    @Test
    public void findAllSessions() {
        Session[] sessions = {
                new Session(LocalDateTime.of(2017, 7, 7, 10, 0)),
                new Session(LocalDateTime.of(2017, 7, 8, 12, 20)),
                new Session(LocalDateTime.of(2017, 7, 9, 14, 15))};
        Arrays.sort(sessions, Comparator.comparing(Session::getSessionStart));
        for (Session session : sessions) {
            sessionDAO.add(session);
        }

        assertArrayEquals(sessions, sessionDAO.findAll().toArray(new Session[0]));
    }

    @Test
    public void addSession() {
        Session session = new Session(LocalDateTime.of(2017, 7, 2, 9, 15));

        assertTrue(sessionDAO.add(session));
        assertEquals(session, sessionDAO.find(session.getId()));
    }

    @Test
    public void updateSession() {
        Session session = new Session(LocalDateTime.of(2017, 7, 2, 9, 15));
        sessionDAO.add(session);
        session.setSessionStart(LocalDateTime.of(2017, 7, 5, 20, 0));

        assertTrue(sessionDAO.update(session));
        assertEquals(session.getSessionStart(),
                sessionDAO.find(session.getId()).getSessionStart());
    }

    @Test
    public void deleteSession() {
        Session session = new Session(LocalDateTime.of(2017, 7, 2, 9, 15));
        sessionDAO.add(session);

        assertTrue(sessionDAO.delete(session.getId()));
        assertNull(sessionDAO.find(session.getId()));
    }
}
