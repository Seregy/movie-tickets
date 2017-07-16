package movietickets.seat.service;

import movietickets.core.WithMockCustomUser;
import movietickets.hall.layout.SeatType;
import movietickets.seat.Seat;
import movietickets.seat.dao.SeatDAO;
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link SeatServiceDAO}.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SeatServiceDAOTest {
    @Autowired
    private SeatService seatService;
    @Autowired
    private SeatDAO seatDAO;
    
    private void addSeat() {
        assertEquals(0, seatDAO.findAll().size());

        Seat seat = new Seat(1, 1, SeatType.REGULAR, 1);
        seatService.add(seat);

        assertEquals(1, seatDAO.findAll().size());
        assertEquals(seat.getRowNumber(), seatDAO.findAll().get(0).getRowNumber());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addSeatUnauthenticated() {
        addSeat();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addSeatWithoutPermission() {
        addSeat();
    }

    @WithMockCustomUser(authorities = "SEAT_ADD_ALL")
    @Test
    public void addSeatWithPermission() {
        addSeat();
    }

    @Test
    public void getSeat() {
        createSeat(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Seat seat2 = createSeat(UUID.fromString("20000000-0000-0000-0000-000000000000"));

        assertEquals(seat2, seatService.get(seat2.getId()));
    }

    @Test
    public void getAllSeats() {
        createSeat(UUID.fromString("10000000-0000-0000-0000-000000000000"));
        createSeat(UUID.fromString("20000000-0000-0000-0000-000000000000"));

        List<Seat> seats = seatService.getAll();
        assertEquals(2, seats.size());
    }

    private void deleteSeat() {
        Seat seat = createSeat(UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertFalse(seatDAO.findAll().isEmpty());
        seatService.delete(seat.getId());
        assertTrue(seatDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteSeatUnauthenticated() {
        deleteSeat();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteSeatWithoutPermission() {
        deleteSeat();
    }

    @WithMockCustomUser(authorities = {"SEAT_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deleteSeatWithSpecificPermission() {
        deleteSeat();
    }

    @WithMockCustomUser(authorities = {"SEAT_DELETE_ALL"})
    @Test
    public void deleteSeatWithGlobalPermission() {
        deleteSeat();
    }

    private void changePrice() {
        Seat seat = createSeat(UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals(1, seatDAO.find(seat.getId()).getPrice());
        seatService.changePrice(seat.getId(), 20);
        assertEquals(20, seatDAO.find(seat.getId()).getPrice());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changePriceUnauthenticated() {
        changePrice();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changePriceWithoutPermission() {
        changePrice();
    }

    @WithMockCustomUser(authorities = {"SEAT_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changePriceWithSpecificPermission() {
        changePrice();
    }

    @WithMockCustomUser(authorities = {"SEAT_EDIT_ALL"})
    @Test
    public void changePriceWithGlobalPermission() {
        changePrice();
    }
    
    private Seat createSeat(final UUID id) {
        Seat seat = new Seat(1, 1, SeatType.REGULAR, 1);
        seat.setId(id);
        seatDAO.add(seat);
        return seat;
    }
}
