package core.web;

import cinema.Cinema;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ticket.Ticket;
import user.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for dealing with API requests, listens '/api/*' path.
 *
 * @author Seregy
 */
@Controller
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

    private final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("cinemaDB");
    private final EntityManager entityManager =
            emf.createEntityManager();

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

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
            Class<?> entityBeanType;

            switch (request.getPathInfo()) {
                case USERS_REQUEST_PATH:
                    entityBeanType = User.class;
                    break;
                case TICKETS_REQUEST_PATH:
                    entityBeanType = Ticket.class;
                    break;
                case CINEMAS_REQUEST_PATH:
                    entityBeanType = Cinema.class;
                    break;
                default:
                    return;
            }

            deleteObject(request, entityBeanType);
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

        Class<?> entityBeanType;
        String attributeName;
        RequestDispatcher requestDispatcher;

        switch (request.getPathInfo()) {
            case USERS_REQUEST_PATH:
                entityBeanType = User.class;
                attributeName = USERS_LIST_ATTRIBUTE;
                requestDispatcher =
                        request.getRequestDispatcher(USERS_JSP_PATH);
                break;
            case TICKETS_REQUEST_PATH:
                entityBeanType = Ticket.class;
                attributeName = TICKETS_LIST_ATTRIBUTE;
                requestDispatcher =
                        request.getRequestDispatcher(TICKETS_JSP_PATH);
                break;
            case CINEMAS_REQUEST_PATH:
                entityBeanType = Cinema.class;
                attributeName = CINEMAS_LIST_ATTRIBUTE;
                requestDispatcher =
                        request.getRequestDispatcher(CINEMAS_JSP_PATH);
                break;
            default:
                return;
        }

        setObjectListAttribute(request, entityBeanType, attributeName);
        requestDispatcher.forward(request, response);
    }

    /**
     * Gets list of objects from specified dao and
     * adds it to the request's attributes.
     *
     * @param request servlet request
     * @param entityBeanType entity class
     * @param attributeName name of the attribute to be set
     */
    private void setObjectListAttribute(final HttpServletRequest request,
                                        final Class<?> entityBeanType,
                                        final String attributeName) {
        entityManager.getTransaction().begin();
        List<?> objects = entityManager
                .createQuery("from " + entityBeanType.getName(), entityBeanType)
                .getResultList();
        entityManager.getTransaction().commit();
        request.setAttribute(attributeName, objects);
    }

    /**
     * Deletes object with ID specified in the request.
     *
     * @param request servlet request
     * @param entityBeanType entity class
     */
    private void deleteObject(final HttpServletRequest request,
                              final Class<?> entityBeanType) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager
                .find(entityBeanType.getClass(),
                        request.getParameter("delete_id")));
        entityManager.getTransaction().commit();
    }


}
