package movietickets.seat.web;

import movietickets.seat.Seat;
import movietickets.seat.service.SeatService;
import movietickets.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Seat controller, responsible for giving pages and resources related to seat.
 *
 * @author Seregy
 */
@Controller
public class SeatController {
    private static Logger log =
            Logger.getLogger(SeatController.class.getName());

    private final SeatService seatService;
    private final TicketService ticketService;

    /**
     * Constructs new seat controller with given Seat Service
     * and Ticket Service.
     *
     * @param seatService seat service
     * @param ticketService ticket service
     */
    @Autowired
    public SeatController(final SeatService seatService,
                          final TicketService ticketService) {
        this.seatService = seatService;
        this.ticketService = ticketService;
    }

    /**
     * Shows main seats' page.
     *
     * @return name of jsp-page
     */
    @GetMapping("/seat")
    public String showSeatsPages() {
        return "admin/seat";
    }

    /**
     * Shows table, filled with seats.
     *
     * @return name of jsp-page
     */
    @GetMapping("/seats")
    public ModelAndView showSeats() {
        ModelAndView modelAndView = new ModelAndView("admin/seats_table");
        modelAndView.addObject("seats", seatService.getAll());
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
        seatService.add(seat);
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
            ticketService.buy(UUID.fromString(id), null);
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
            ticketService.reserve(UUID.fromString(id), null);
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
        seatService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
