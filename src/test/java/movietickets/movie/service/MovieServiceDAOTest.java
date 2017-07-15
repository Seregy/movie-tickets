package movietickets.movie.service;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.city.City;
import movietickets.city.dao.CityDAO;
import movietickets.core.WithMockCustomUser;
import movietickets.hall.Hall;
import movietickets.hall.dao.HallDAO;
import movietickets.movie.Movie;
import movietickets.movie.dao.MovieDAO;
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
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Tests for {@link MovieServiceDAO}.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MovieServiceDAOTest {
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieDAO movieDAO;
    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private CinemaDAO cinemaDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private HallDAO hallDAO;

    private void addMovie() {
        assertEquals(0, movieDAO.findAll().size());

        Movie movie = new Movie("movie", 100, "annotation");
        movieService.add(movie);

        assertEquals(1, movieDAO.findAll().size());
        assertEquals(movie.getName(), movieDAO.findAll().get(0).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addMovieUnauthenticated() {
        addMovie();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addMovieWithoutPermission() {
        addMovie();
    }

    @WithMockCustomUser(authorities = "MOVIE_ADD_ALL")
    @Test
    public void addMovieWithPermission() {
        addMovie();
    }

    @Test
    public void getMovie() {
        createMovie("movie1", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Movie movie2 = createMovie("movie2", UUID.fromString("20000000-0000-0000-0000-000000000000"));

        assertEquals(movie2, movieService.get(movie2.getId()));
    }

    @Test
    public void getAllMovies() {
        createMovie("movie1", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        createMovie("movie2", UUID.fromString("20000000-0000-0000-0000-000000000000"));

        List<Movie> movies = movieService.getAll();
        assertEquals(2, movies.size());
    }

    @Test
    public void getAllAvailableMovies() {
        Movie movie1 = createMovie("movie1", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Movie movie2 = createMovie("movie2", UUID.fromString("20000000-0000-0000-0000-000000000000"));
        movie2.setScreeningDate(LocalDate.now().plusMonths(1));
        movieDAO.update(movie2);
        Movie movie3 = createMovie("movie3", UUID.fromString("30000000-0000-0000-0000-000000000000"));
        movie3.addSession(createSession(LocalDateTime.now().plusMonths(1)));
        movieDAO.update(movie3);

        List<Movie> movies = movieService.getAllAvailable();
        assertEquals(2, movies.size());
        assertFalse(movies.contains(movie1));
    }

    @Test
    public void getAllAvailableMoviesByCity() {
        createMovie("movie1", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Movie movie2 = createMovie("movie2", UUID.fromString("20000000-0000-0000-0000-000000000000"));
        movie2.setScreeningDate(LocalDate.now().minusMonths(1));
        movieDAO.update(movie2);
        Movie movie3 = createMovie("movie3", UUID.fromString("30000000-0000-0000-0000-000000000000"));
        Session session = createSession(LocalDateTime.now().plusMonths(1));
        City city = createCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema = createCinema(city);
        session.setHall(createHall(cinema));
        movie3.addSession(session);
        movieDAO.update(movie3);

        List<Movie> movies = movieService.getAllAvailable(city);
        assertEquals(1, movies.size());
        assertEquals(movie3, movies.get(0));
    }

    private void deleteMovie() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));

        int originalSize = movieDAO.findAll().size();
        movieService.delete(movie.getId());
        assertNotEquals(originalSize, movieDAO.findAll().size());
        assertTrue(movieDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteMovieUnauthenticated() {
        deleteMovie();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteMovieWithoutPermission() {
        deleteMovie();
    }

    @WithMockCustomUser(authorities = {"MOVIE_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deleteMovieWithSpecificPermission() {
        deleteMovie();
    }

    @WithMockCustomUser(authorities = {"MOVIE_DELETE_ALL"})
    @Test
    public void deleteMovieWithGlobalPermission() {
        deleteMovie();
    }

    private void changeName() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("movie", movieDAO.find(movie.getId()).getName());
        movieService.changeName(movie.getId(), "newName");
        assertEquals("newName", movieDAO.find(movie.getId()).getName());
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

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeNameWithSpecificPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeNameWithGlobalPermission() {
        changeName();
    }

    private void changeDuration() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals(100, movieDAO.find(movie.getId()).getDuration());
        movieService.changeDuration(movie.getId(), 200);
        assertEquals(200, movieDAO.find(movie.getId()).getDuration());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeDurationUnauthenticated() {
        changeDuration();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeDurationWithoutPermission() {
        changeDuration();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeDurationWithSpecificPermission() {
        changeDuration();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeDurationWithGlobalPermission() {
        changeDuration();
    }

    private void changeAnnotation() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("annotation", movieDAO.find(movie.getId()).getAnnotation());
        movieService.changeAnnotation(movie.getId(), "newAnnotation");
        assertEquals("newAnnotation", movieDAO.find(movie.getId()).getAnnotation());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeAnnotationUnauthenticated() {
        changeAnnotation();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeAnnotationWithoutPermission() {
        changeAnnotation();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeAnnotationWithSpecificPermission() {
        changeAnnotation();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeAnnotationWithGlobalPermission() {
        changeAnnotation();
    }

    private void changeYear() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setYear(2000);
        movieDAO.update(movie);

        assertEquals(2000, movieDAO.find(movie.getId()).getYear());
        movieService.changeYear(movie.getId(), 2010);
        assertEquals(2010, movieDAO.find(movie.getId()).getYear());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeYearUnauthenticated() {
        changeYear();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeYearWithoutPermission() {
        changeYear();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeYearWithSpecificPermission() {
        changeYear();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeYearWithGlobalPermission() {
        changeYear();
    }

    private void changeCountry() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setCountry("country");
        movieDAO.update(movie);

        assertEquals("country", movieDAO.find(movie.getId()).getCountry());
        movieService.changeCountry(movie.getId(), "newCountry");
        assertEquals("newCountry", movieDAO.find(movie.getId()).getCountry());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeCountryUnauthenticated() {
        changeCountry();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeCountryWithoutPermission() {
        changeCountry();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeCountryWithSpecificPermission() {
        changeCountry();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeCountryWithGlobalPermission() {
        changeCountry();
    }

    private void changeGenres() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setGenres("genres");
        movieDAO.update(movie);

        assertEquals("genres", movieDAO.find(movie.getId()).getGenres());
        movieService.changeGenres(movie.getId(), "newGenres");
        assertEquals("newGenres", movieDAO.find(movie.getId()).getGenres());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeGenresUnauthenticated() {
        changeGenres();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeGenresWithoutPermission() {
        changeGenres();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeGenresWithSpecificPermission() {
        changeGenres();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeGenresWithGlobalPermission() {
        changeGenres();
    }

    private void changeCast() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setCast("cast");
        movieDAO.update(movie);

        assertEquals("cast", movieDAO.find(movie.getId()).getCast());
        movieService.changeCast(movie.getId(), "newCast");
        assertEquals("newCast", movieDAO.find(movie.getId()).getCast());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeCastUnauthenticated() {
        changeCast();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeCastWithoutPermission() {
        changeCast();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeCastWithSpecificPermission() {
        changeCast();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeCastWithGlobalPermission() {
        changeCast();
    }

    private void changeDirector() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setDirector("director");
        movieDAO.update(movie);

        assertEquals("director", movieDAO.find(movie.getId()).getDirector());
        movieService.changeDirector(movie.getId(), "newDirector");
        assertEquals("newDirector", movieDAO.find(movie.getId()).getDirector());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeDirectorUnauthenticated() {
        changeDirector();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeDirectorWithoutPermission() {
        changeDirector();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeDirectorWithSpecificPermission() {
        changeDirector();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeDirectorWithGlobalPermission() {
        changeDirector();
    }

    private void changeScreeningDate() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setScreeningDate(LocalDate.of(2017, 7, 15));
        movieDAO.update(movie);

        assertEquals(LocalDate.of(2017, 7, 15),
                movieDAO.find(movie.getId()).getScreeningDate());
        movieService.changeScreeningDate(movie.getId(), LocalDate.of(2018, 7, 15));
        assertEquals(LocalDate.of(2018, 7, 15),
                movieDAO.find(movie.getId()).getScreeningDate());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeScreeningDateUnauthenticated() {
        changeScreeningDate();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeScreeningDateWithoutPermission() {
        changeScreeningDate();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeScreeningDateWithSpecificPermission() {
        changeScreeningDate();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeScreeningDateWithGlobalPermission() {
        changeScreeningDate();
    }

    private void changePremiereEndDate() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setPremiereEndDate(LocalDate.of(2017, 7, 15));
        movieDAO.update(movie);

        assertEquals(LocalDate.of(2017, 7, 15),
                movieDAO.find(movie.getId()).getPremiereEndDate());
        movieService.changePremiereEndDate(movie.getId(), LocalDate.of(2018, 7, 15));
        assertEquals(LocalDate.of(2018, 7, 15),
                movieDAO.find(movie.getId()).getPremiereEndDate());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changePremiereEndDateUnauthenticated() {
        changePremiereEndDate();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changePremiereEndDateWithoutPermission() {
        changePremiereEndDate();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changePremiereEndDateWithSpecificPermission() {
        changePremiereEndDate();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changePremiereEndDateWithGlobalPermission() {
        changePremiereEndDate();
    }

    private void changeContentRating() {
        Movie movie = createMovie("movie", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        movie.setContentRating("contentRating");
        movieDAO.update(movie);

        assertEquals("contentRating", movieDAO.find(movie.getId()).getContentRating());
        movieService.changeContentRating(movie.getId(), "newContentRating");
        assertEquals("newContentRating", movieDAO.find(movie.getId()).getContentRating());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeContentRatingUnauthenticated() {
        changeContentRating();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeContentRatingWithoutPermission() {
        changeContentRating();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeContentRatingWithSpecificPermission() {
        changeContentRating();
    }

    @WithMockCustomUser(authorities = {"MOVIE_EDIT_ALL"})
    @Test
    public void changeContentRatingWithGlobalPermission() {
        changeContentRating();
    }

    private City createCity(final UUID id) {
        City city = new City("city");
        city.setId(id);
        cityDAO.add(city);
        return city;
    }

    private Cinema createCinema(final City city) {
        Cinema cinema = new Cinema("cinema", "address");
        cinema.setId(UUID.randomUUID());
        city.addCinema(cinema);
        cinemaDAO.add(cinema);
        cityDAO.update(city);
        return cinema;
    }

    private Movie createMovie(final String name,
                              final UUID id) {
        Movie movie = new Movie(name, 100, "annotation");
        movie.setScreeningDate(LocalDate.now().minusMonths(1));
        movie.setId(id);
        movieDAO.add(movie);
        return movie;
    }

    private Session createSession(LocalDateTime time) {
        Session session = new Session(time, "2D", 10);
        session.setId(UUID.randomUUID());
        sessionDAO.add(session);
        return session;
    }

    private Hall createHall(final Cinema cinema) {
        Hall hall = new Hall("hall");
        hall.setId(UUID.randomUUID());
        cinema.addHall(hall);
        hallDAO.add(hall);
        cinemaDAO.update(cinema);
        return hall;
    }
}
