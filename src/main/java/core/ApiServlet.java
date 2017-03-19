package core;

import cinema.CinemaDAO;
import cinema.CinemaDAODefault;
import ticket.TicketDAO;
import ticket.TicketDAODefault;
import user.UserDAO;
import user.UserDAODefault;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Servlet for dealing with API requests, listens '/api/*' path.
 *
 * @author Seregy
 */
public final class ApiServlet extends HttpServlet {
    private static final String USERS_REQUEST_PATH = "/users";
    private static final String TICKETS_REQUEST_PATH = "/tickets";
    private static final String CINEMAS_REQUEST_PATH = "/cinemas";

    private static final String USERS_LIST_ATTRIBUTE = "users";
    private static final String TICKETS_LIST_ATTRIBUTE = "tickets";
    private static final String CINEMAS_LIST_ATTRIBUTE = "cinemas";

    private static final String USERS_JSP_PATH =
            "../WEB-INF/users_table.jsp";
    private static final String TICKETS_JSP_PATH =
            "../WEB-INF/tickets_table.jsp";
    private static final String CINEMAS_JSP_PATH =
            "../WEB-INF/cinemas_table.jsp";

    private final UserDAO userDAO = new UserDAODefault();
    private final TicketDAO ticketDAO = new TicketDAODefault();
    private final CinemaDAO cinemaDAO = new CinemaDAODefault();

    /**
     * {@inheritDoc}
     */
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        if (request.getParameter("delete_id") != null) {
            if (request.getPathInfo() == null) {
                return;
            }

            DAO<?> dao;

            switch (request.getPathInfo()) {
                case USERS_REQUEST_PATH:
                    dao = userDAO;
                    break;
                case TICKETS_REQUEST_PATH:
                    dao = ticketDAO;
                    break;
                case CINEMAS_REQUEST_PATH:
                    dao = cinemaDAO;
                    break;
                default:
                    return;
            }

            deleteObject(request, dao);
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        if (request.getPathInfo() == null) {
            return;
        }

        DAO<?> dao;
        String attributeName;
        RequestDispatcher requestDispatcher;

        switch (request.getPathInfo()) {
            case USERS_REQUEST_PATH:
                dao = userDAO;
                attributeName = USERS_LIST_ATTRIBUTE;
                requestDispatcher =
                        request.getRequestDispatcher(USERS_JSP_PATH);
                System.out.println("In users path");
                break;
            case TICKETS_REQUEST_PATH:
                dao = ticketDAO;
                attributeName = TICKETS_LIST_ATTRIBUTE;
                requestDispatcher =
                        request.getRequestDispatcher(TICKETS_JSP_PATH);
                break;
            case CINEMAS_REQUEST_PATH:
                dao = cinemaDAO;
                attributeName = CINEMAS_LIST_ATTRIBUTE;
                requestDispatcher =
                        request.getRequestDispatcher(CINEMAS_JSP_PATH);
                break;
            default:
                return;
        }

        setObjectListAttribute(request, dao, attributeName);
        requestDispatcher.forward(request, response);
    }

    /**
     * Gets list of objects from specified dao and
     * adds it to the request's attributes.
     *
     * @param request servlet request
     * @param dao {@link DAO} object
     * @param attributeName name of the attribute to be set
     */
    private void setObjectListAttribute(final HttpServletRequest request,
                                        final DAO<?> dao,
                                        final String attributeName) {
        List<?> objects = dao.findAll();
        request.setAttribute(attributeName, objects);
    }

    /**
     * Deletes object with ID specified in the request.
     *
     * @param request servlet request
     * @param dao {@link DAO} object
     */
    private void deleteObject(final HttpServletRequest request,
                              final DAO<?> dao) {
        dao.delete(UUID.fromString(request.getParameter("delete_id")));
    }
}
