package movietickets.seat.dao;

import movietickets.seat.Seat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Tests for Seat data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.TestConfiguration.class)
@Transactional
public class SeatDAOTest {
    @Autowired
    private SeatDAO seatDAO;

    @Test
    public void findSeat() {
        Seat[] seats = {
                new Seat(1, 1),
                new Seat(2, 2),
                new Seat(3, 3)};
        for (Seat seat : seats) {
            seatDAO.add(seat);
        }

        assertEquals(seats[1], seatDAO.find(seats[1].getId()));
    }

    @Test
    public void findAllSeats() {
        Seat[] seats = {
                new Seat(1, 1),
                new Seat(2, 2),
                new Seat(3, 3),
                new Seat(4, 4)};
        for (Seat seat : seats) {
            seatDAO.add(seat);
        }

        assertArrayEquals(seats, seatDAO.findAll().toArray(new Seat[0]));
    }

    @Test
    public void addSeat() {
        Seat seat = new Seat(7, 7);

        assertTrue(seatDAO.add(seat));
        assertEquals(seat, seatDAO.find(seat.getId()));
    }

    @Test
    public void updateSeat() {
        Seat seat = new Seat(7, 7);
        seatDAO.add(seat);
        seat.setRowNumber(17);

        assertTrue(seatDAO.update(seat));
        assertEquals(seat.getRowNumber(),
                seatDAO.find(seat.getId()).getRowNumber());
    }

    @Test
    public void deleteSeat() {
        Seat seat = new Seat(7, 7);
        seatDAO.add(seat);

        assertTrue(seatDAO.delete(seat.getId()));
        assertNull(seatDAO.find(seat.getId()));
    }
}
