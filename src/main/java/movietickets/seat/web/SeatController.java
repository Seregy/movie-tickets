package movietickets.seat.web;

import movietickets.seat.Seat;
import movietickets.seat.SeatStatus;
import movietickets.seat.dao.SeatDAO;
import movietickets.ticket.Ticket;
import movietickets.ticket.dao.TicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Seat controller, responsible for giving pages and resources related to seat.
 *
 * @author Seregy
 */
@Controller
@Transactional
public class SeatController {
    private static Logger log =
            Logger.getLogger(SeatController.class.getName());

    private final SeatDAO seatDAO;
    private final TicketDAO ticketDAO;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs new seat controller with given Seat DAO and Ticket DAO.
     *
     * @param seatDAO seat data access object
     * @param ticketDAO ticket data access object
     */
    @Autowired
    public SeatController(final SeatDAO seatDAO, final TicketDAO ticketDAO) {
        this.seatDAO = seatDAO;
        this.ticketDAO = ticketDAO;
    }

    /**
     * Shows main seats' page.
     *
     * @return name of jsp-page
     */
    @GetMapping("/seat")
    public String showSeatsPages() {
        return "seat";
    }

    /**
     * Shows table, filled with seats.
     *
     * @return name of jsp-page
     */
    @GetMapping("/seats")
    public ModelAndView showSeats() {
        ModelAndView modelAndView = new ModelAndView("seats_table");
        modelAndView.addObject("seats", seatDAO.findAll());
        return modelAndView;
    }

    /**
     * Adds new seat with given row and seat number.
     *
     * @param rowNumber  number of row
     * @param seatNumber number of seat
     * @return response code
     */
    @PostMapping("/seats")
    public ResponseEntity addSeat(@RequestParam("row_number")
                                    final int rowNumber,
                                  @RequestParam("seat_number")
                                    final int seatNumber) {
        Seat seat = new Seat(rowNumber, seatNumber);
        seatDAO.add(seat);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Marks given seats as bought if the seats are available for purchase.
     *
     * @param ids list of cinemas' identifiers
     * @return response code
     */
    @GetMapping("/seat/buy")
    public ResponseEntity buySeats(@RequestParam("ids[]")
                                       final List<String> ids) {
        for (String id : ids) {
            Seat seat = seatDAO.find(UUID.fromString(id));
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            seat.setSeatStatus(SeatStatus.PURCHASED);
            ticketDAO.add(new Ticket(seat, null));
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Marks given seats as reserved if the seats are available for reservation.
     *
     * @param ids list of cinemas' identifiers
     * @return response code
     */
    @GetMapping("/seat/reserve")
    public ResponseEntity reserveSeats(@RequestParam("ids[]")
                                           final String[] ids) {
        for (String id : ids) {
            Seat seat = seatDAO.find(UUID.fromString(id));
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            seat.setSeatStatus(SeatStatus.RESERVED);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes seat with given id.
     *
     * @param id identifier of the seat
     * @return response code
     */
    @DeleteMapping("/seats/{id}")
    public ResponseEntity deleteSeat(@PathVariable("id") final String id) {
        seatDAO.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
