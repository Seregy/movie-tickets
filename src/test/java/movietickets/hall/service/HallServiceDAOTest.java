package movietickets.hall.service;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.core.WithMockCustomUser;
import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.hall.layout.Layout;
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
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link HallServiceDAO}.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HallServiceDAOTest {
    @Autowired
    private HallService hallService;
    @Autowired
    private HallDAO hallDAO;
    @Autowired
    private CinemaDAO cinemaDAO;

    private void addHall() {
        assertEquals(0, hallDAO.findAll().size());

        Hall hall = new Hall("hall");
        Cinema cinema = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        hallService.add(hall,
                createLayout(1, 1),
                cinema.getId());

        assertEquals(1, hallDAO.findAll().size());
        assertEquals(hall.getName(), hallDAO.findAll().get(0).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addHallUnauthenticated() {
        addHall();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addHallWithoutPermission() {
        addHall();
    }

    @WithMockCustomUser(authorities = "HALL_ADD_ALL")
    @Test
    public void addHallWithPermission() {
        addHall();
    }

    @Test
    public void getHall() {
        Cinema cinema = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        createHall("hall1", cinema, UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Hall hall2 = createHall("hall2", cinema, UUID.fromString("20000000-0000-0000-0000-000000000000"));

        assertEquals(hall2, hallService.get(hall2.getId()));
    }

    @Test
    public void getAllHalls() {
        Cinema cinema1 = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema2 = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        createHall("hall1", cinema1, UUID.fromString("10000000-0000-0000-0000-000000000000"));
        createHall("hall2", cinema2, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        List<Hall> halls = hallService.getAll();
        assertEquals(2, halls.size());
    }

    @Test
    public void getAllHallsByCinemaId() {
        Cinema cinema1 = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema2 = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        createHall("hall1", cinema1, UUID.fromString("10000000-0000-0000-0000-000000000000"));
        createHall("hall2", cinema2, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        List<Hall> halls = hallService.getAll(cinema2.getId());
        assertEquals(1, halls.size());
        assertEquals(cinema2, halls.get(0).getCinema());
    }

    private void deleteHall() {
        Cinema cinema = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Hall hall = createHall("hall", cinema, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        int originalSize = hallDAO.findAll().size();
        hallService.delete(hall.getId());
        assertNotEquals(originalSize, hallDAO.findAll().size());
        assertTrue(hallDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteHallUnauthenticated() {
        deleteHall();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteHallWithoutPermission() {
        deleteHall();
    }

    @WithMockCustomUser(authorities = {"HALL_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deleteHallWithSpecificPermission() {
        deleteHall();
    }

    @WithMockCustomUser(authorities = {"HALL_DELETE_ALL"})
    @Test
    public void deleteHallWithGlobalPermission() {
        deleteHall();
    }

    private void changeName() {
        Cinema cinema = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Hall hall = createHall("hall", cinema, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("hall", hallDAO.find(hall.getId()).getName());
        hallService.changeName(hall.getId(), "newName");
        assertEquals("newName", hallDAO.find(hall.getId()).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeNameUnauthenticated() {
        changeName();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeNameWithoutPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"HALL_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeNameWithSpecificPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"HALL_EDIT_ALL"})
    @Test
    public void changeNameWithGlobalPermission() {
        changeName();
    }

    private void changeLayout() {
        Cinema cinema = createCinema(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Hall hall = createHall("hall", cinema, UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Layout layout1 = createLayout(1, 1);
        Layout layout2 = createLayout(2, 2);
        hall.setLayout(layout1);
        hallDAO.update(hall);

        assertEquals(layout1, hallDAO.find(hall.getId()).getLayout());
        hallService.changeLayout(hall.getId(), layout2);
        assertEquals(layout2, hallDAO.find(hall.getId()).getLayout());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeLayoutUnauthenticated() {
        changeLayout();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeLayoutWithoutPermission() {
        changeLayout();
    }

    @WithMockCustomUser(authorities = {"HALL_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeLayoutWithSpecificPermission() {
        changeLayout();
    }

    @WithMockCustomUser(authorities = {"HALL_EDIT_ALL"})
    @Test
    public void changeLayoutWithGlobalPermission() {
        changeLayout();
    }

    private Layout createLayout(final int rows,
                                final int seats) {
        return new Layout(rows, seats);
    }

    private Cinema createCinema(final UUID id) {
        Cinema cinema = new Cinema("cinema",
                "address",
                "phone",
                "website");
        cinema.setId(id);
        cinemaDAO.add(cinema);
        return cinema;
    }

    private Hall createHall(final String name,
                            final Cinema cinema,
                            final UUID id) {
        Hall hall = new Hall(name);
        hall.setId(id);
        hallDAO.add(hall);
        cinema.addHall(hall);
        cinemaDAO.update(cinema);
        return hall;
    }
}
