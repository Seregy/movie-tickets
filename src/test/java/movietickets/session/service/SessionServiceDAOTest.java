package movietickets.session.service;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.core.WithMockCustomUser;
import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
import movietickets.hall.layout.SeatType;
import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
import movietickets.seat.Seat;
import movietickets.session.Session;
import movietickets.session.dao.SessionDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link SessionServiceDAO}.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SessionServiceDAOTest {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private MovieDAO movieDAO;
    @Autowired
    private HallDAO hallDAO;
    @Autowired
    private CinemaDAO cinemaDAO;

    private void addSession() {
        assertEquals(0, sessionDAO.findAll().size());

        Session session = new Session(LocalDateTime.now(), "technology", 10);
        Hall hall = createHall(createCinema(UUID.randomUUID()), UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        sessionService.add(session, hall.getId(), movie.getId());

        assertEquals(1, sessionDAO.findAll().size());
        assertEquals(session.getSessionStart(), sessionDAO.findAll().get(0).getSessionStart());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addSessionUnauthenticated() {
        addSession();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addSessionWithoutPermission() {
        addSession();
    }

    @WithMockCustomUser(authorities = "SESSION_ADD_ALL")
    @Test
    public void addSessionWithPermission() {
        addSession();
    }

    @Test
    public void getSession() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall1 = createHall(cinema, UUID.randomUUID());
        Hall hall2 = createHall(cinema, UUID.randomUUID());
        Movie movie1 = createMovie(UUID.randomUUID());
        Movie movie2 = createMovie(UUID.randomUUID());
        createSession(hall1, movie1, LocalDateTime.now(), UUID.randomUUID());
        Session session2 = createSession(hall2, movie2, LocalDateTime.now(), UUID.randomUUID());

        assertEquals(session2, sessionService.get(session2.getId()));
    }

    @Test
    public void getAllSessions() {
        Cinema cinema1 = createCinema(UUID.randomUUID());
        Cinema cinema2 = createCinema(UUID.randomUUID());
        Hall hall1 = createHall(cinema1, UUID.randomUUID());
        Hall hall2 = createHall(cinema2, UUID.randomUUID());
        Movie movie1 = createMovie(UUID.randomUUID());
        Movie movie2 = createMovie(UUID.randomUUID());
        createSession(hall1, movie1, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall2, movie2, LocalDateTime.now(), UUID.randomUUID());

        List<Session> sessions = sessionService.getAll();
        assertEquals(2, sessions.size());
    }

    @Test
    public void getAllSessionsByCinema() {
        Cinema cinema1 = createCinema(UUID.randomUUID());
        Cinema cinema2 = createCinema(UUID.randomUUID());
        Hall hall1 = createHall(cinema1, UUID.randomUUID());
        Hall hall2 = createHall(cinema2, UUID.randomUUID());
        Movie movie1 = createMovie(UUID.randomUUID());
        Movie movie2 = createMovie(UUID.randomUUID());
        Session session1 = createSession(hall1, movie1, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall2, movie2, LocalDateTime.now(), UUID.randomUUID());

        List<Session> sessions = sessionService.getAll(cinema2.getId());
        assertEquals(1, sessions.size());
        assertFalse(sessions.contains(session1));
    }

    @Test
    public void getAllFutureSessions() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall = createHall(cinema, UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        Session session1 = createSession(hall, movie, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        Session session2 = createSession(hall, movie, LocalDateTime.now(), UUID.randomUUID());
        createSession(hall, movie, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        List<Session> sessions = sessionService.getAllFuture();
        assertEquals(1, sessions.size());
        assertFalse(sessions.contains(session1));
        assertFalse(sessions.contains(session2));
    }

    @Test
    public void getAllFutureSessionsByMovie() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall = createHall(cinema, UUID.randomUUID());
        Movie movie1 = createMovie(UUID.randomUUID());
        Movie movie2 = createMovie(UUID.randomUUID());
        createSession(hall, movie1, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall, movie1, LocalDateTime.now(), UUID.randomUUID());
        createSession(hall, movie1, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        
        createSession(hall, movie2, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall, movie2, LocalDateTime.now(), UUID.randomUUID());
        Session session6 = createSession(hall, movie2, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        List<Session> sessions = sessionService.getAllFuture(movie2.getId());
        assertEquals(1, sessions.size());
        assertEquals(session6, sessions.get(0));
    }

    @Test
    public void getAllFutureSessionsByMovieAndCinema() {
        Cinema cinema1 = createCinema(UUID.randomUUID());
        Cinema cinema2 = createCinema(UUID.randomUUID());
        Hall hall1 = createHall(cinema1, UUID.randomUUID());
        Hall hall2 = createHall(cinema2, UUID.randomUUID());
        Movie movie1 = createMovie(UUID.randomUUID());
        Movie movie2 = createMovie(UUID.randomUUID());
        
        createSession(hall1, movie1, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall1, movie1, LocalDateTime.now(), UUID.randomUUID());
        createSession(hall1, movie1, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        createSession(hall1, movie2, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall1, movie2, LocalDateTime.now(), UUID.randomUUID());
        createSession(hall1, movie2, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        createSession(hall2, movie1, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall2, movie1, LocalDateTime.now(), UUID.randomUUID());
        createSession(hall2, movie1, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        createSession(hall2, movie2, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        createSession(hall2, movie2, LocalDateTime.now(), UUID.randomUUID());
        Session session12 = createSession(hall2, movie2, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        List<Session> sessions = sessionService.getAllFuture(movie2.getId(), cinema2.getId());
        assertEquals(1, sessions.size());
        assertEquals(session12, sessions.get(0));
    }

    private void deleteSession() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall = createHall(cinema, UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        Session session = createSession(hall, movie,
                LocalDateTime.now(),
                UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertFalse(sessionDAO.findAll().isEmpty());
        sessionService.delete(session.getId());
        assertTrue(sessionDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteSessionUnauthenticated() {
        deleteSession();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteSessionWithoutPermission() {
        deleteSession();
    }

    @WithMockCustomUser(authorities = {"SESSION_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deleteSessionWithSpecificPermission() {
        deleteSession();
    }

    @WithMockCustomUser(authorities = {"SESSION_DELETE_ALL"})
    @Test
    public void deleteSessionWithGlobalPermission() {
        deleteSession();
    }

    private void changeTime() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall = createHall(cinema, UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        LocalDateTime time = LocalDateTime.now();
        Session session = createSession(hall, movie,
                time,
                UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals(time, sessionDAO.find(session.getId()).getSessionStart());
        sessionService.changeTime(session.getId(), time.plusDays(2));
        assertEquals(time.plusDays(2), sessionDAO.find(session.getId()).getSessionStart());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeTimeUnauthenticated() {
        changeTime();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeTimeWithoutPermission() {
        changeTime();
    }

    @WithMockCustomUser(authorities = {"SESSION_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeTimeWithSpecificPermission() {
        changeTime();
    }

    @WithMockCustomUser(authorities = {"SESSION_EDIT_ALL"})
    @Test
    public void changeTimeWithGlobalPermission() {
        changeTime();
    }

    private void changeHall() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall1 = createHall(cinema, UUID.randomUUID());
        Hall hall2 = createHall(cinema, UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        Session session = createSession(hall1, movie,
                LocalDateTime.now(),
                UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals(hall1, sessionDAO.find(session.getId()).getHall());
        sessionService.changeHall(session.getId(), hall2.getId());
        assertEquals(hall2, sessionDAO.find(session.getId()).getHall());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeHallUnauthenticated() {
        changeHall();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeHallWithoutPermission() {
        changeHall();
    }

    @WithMockCustomUser(authorities = {"SESSION_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeHallWithSpecificPermission() {
        changeHall();
    }

    @WithMockCustomUser(authorities = {"SESSION_EDIT_ALL"})
    @Test
    public void changeHallWithGlobalPermission() {
        changeHall();
    }

    @Test
    public void getDisplayedSeats() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall = createHall(cinema, UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        Session session = createSession(hall, movie,
                LocalDateTime.now(),
                UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Seat[][] seats = sessionService.getDisplayedSeats(session.getId());

        assertEquals(3, seats.length);
        assertEquals(3, seats[0].length);
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                assertEquals(SeatType.REGULAR, seat.getSeatType());
            }
        }
    }

    @Test
    public void groupByCinema() {
        Cinema cinema1 = createCinema(UUID.randomUUID());
        Cinema cinema2 = createCinema(UUID.randomUUID());
        Hall hall1 = createHall(cinema1, UUID.randomUUID());
        Hall hall2 = createHall(cinema2, UUID.randomUUID());
        Movie movie1 = createMovie(UUID.randomUUID());
        Movie movie2 = createMovie(UUID.randomUUID());
        Session session1 = createSession(hall1, movie1, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        Session session2 = createSession(hall2, movie2, LocalDateTime.now(), UUID.randomUUID());
        Map<Cinema, List<Session>> grouped = sessionService.groupByCinema(sessionDAO.findAll());

        assertTrue(grouped.containsKey(cinema1));
        assertTrue(grouped.containsKey(cinema2));
        assertEquals(2, grouped.keySet().size());
        assertEquals(1, grouped.get(cinema1).size());
        assertEquals(session1, grouped.get(cinema1).get(0));
        assertEquals(1, grouped.get(cinema2).size());
        assertEquals(session2, grouped.get(cinema2).get(0));
    }

    @Test
    public void groupByMovie() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall = createHall(cinema, UUID.randomUUID());
        Movie movie1 = createMovie(UUID.randomUUID());
        Movie movie2 = createMovie(UUID.randomUUID());
        Session session1 = createSession(hall, movie1, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        Session session2 = createSession(hall, movie2, LocalDateTime.now(), UUID.randomUUID());
        Map<Movie, List<Session>> grouped = sessionService.groupByMovie(sessionDAO.findAll());

        assertTrue(grouped.containsKey(movie1));
        assertTrue(grouped.containsKey(movie2));
        assertEquals(2, grouped.keySet().size());
        assertEquals(1, grouped.get(movie1).size());
        assertEquals(session1, grouped.get(movie1).get(0));
        assertEquals(1, grouped.get(movie2).size());
        assertEquals(session2, grouped.get(movie2).get(0));
    }

    @Test
    public void groupByDate() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall = createHall(cinema, UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(1);
        Session session1 = createSession(hall, movie,
                LocalDateTime.of(date1, LocalTime.of(10, 10)), UUID.randomUUID());
        Session session2 = createSession(hall, movie,
                LocalDateTime.of(date2, LocalTime.of(12, 10)), UUID.randomUUID());
        Map<LocalDate, List<Session>> grouped = sessionService.groupByDate(sessionDAO.findAll());

        assertTrue(grouped.containsKey(date1));
        assertTrue(grouped.containsKey(date2));
        assertEquals(2, grouped.keySet().size());
        assertEquals(1, grouped.get(date1).size());
        assertEquals(session1, grouped.get(date1).get(0));
        assertEquals(1, grouped.get(date2).size());
        assertEquals(session2, grouped.get(date2).get(0));
    }

    @Test
    public void groupByHall() {
        Cinema cinema = createCinema(UUID.randomUUID());
        Hall hall1 = createHall(cinema, UUID.randomUUID());
        Hall hall2 = createHall(cinema, UUID.randomUUID());
        Movie movie = createMovie(UUID.randomUUID());
        Session session1 = createSession(hall1, movie, LocalDateTime.now().minusDays(1), UUID.randomUUID());
        Session session2 = createSession(hall2, movie, LocalDateTime.now(), UUID.randomUUID());
        Map<Hall, List<Session>> grouped = sessionService.groupByHall(sessionDAO.findAll());

        assertTrue(grouped.containsKey(hall1));
        assertTrue(grouped.containsKey(hall2));
        assertEquals(2, grouped.keySet().size());
        assertEquals(1, grouped.get(hall1).size());
        assertEquals(session1, grouped.get(hall1).get(0));
        assertEquals(1, grouped.get(hall2).size());
        assertEquals(session2, grouped.get(hall2).get(0));
    }

    private Cinema createCinema(final UUID id) {
        Cinema cinema = new Cinema("cinema" + id.toString(), "address");
        cinema.setId(id);
        cinemaDAO.add(cinema);
        return cinema;
    }

    private Hall createHall(final Cinema cinema,
                            final UUID id) {
        Hall hall = new Hall("hall" + id.toString());
        Layout layout = new Layout(3, 3);
        hall.setLayout(layout);
        hall.setId(id);
        cinema.addHall(hall);
        hallDAO.add(hall);
        cinemaDAO.update(cinema);
        return hall;
    }

    private Movie createMovie(final UUID id) {
        Movie movie = new Movie("movie" + id.toString(), 10, "annotation");
        movie.setId(id);
        movieDAO.add(movie);
        return movie;
    }

    private Session createSession(final Hall hall,
                                  final Movie movie,
                                  final LocalDateTime startTime,
                                  final UUID id) {
        Session session = new Session(startTime, "technology", 10);
        session.setId(id);

        boolean rowChanged = false;
        int actualRow = 1;
        for (SeatType[] typeRow : hall.getLayout().getSeatsTypes()) {
            int actualSeat = 1;
            for (SeatType seatType : typeRow) {
                if (seatType == SeatType.REGULAR) {
                    if (rowChanged) {
                        actualRow++;
                        rowChanged = false;
                    }
                    session.addSeat(new Seat(actualRow, actualSeat++,
                            seatType, session.getDefaultPrice()));
                }
            }
            rowChanged = true;
        }

        hall.addSession(session);
        movie.addSession(session);
        sessionDAO.add(session);
        hallDAO.update(hall);
        movieDAO.update(movie);
        return session;
    }
}
