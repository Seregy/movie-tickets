package movietickets.cinema.service;

import movietickets.cinema.Cinema;
import movietickets.cinema.dao.CinemaDAO;
import movietickets.city.City;
import movietickets.city.dao.CityDAO;
import movietickets.core.WithMockCustomUser;
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

/**
 * Tests for CinemaServiceDAO.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CinemaServiceDAOTest {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private CinemaDAO cinemaDAO;
    @Autowired
    private CityDAO cityDAO;

    private void addCinema() {
        assertEquals(0, cinemaDAO.findAll().size());

        Cinema cinema = new Cinema("name", "address");
        City city = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        cinemaService.add(cinema, city.getId());

        assertEquals(1, cinemaDAO.findAll().size());
        assertEquals(cinema.getName(), cinemaDAO.findAll().get(0).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addCinemaUnauthenticated() {
        addCinema();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addCinemaWithoutPermission() {
        addCinema();
    }

    @WithMockCustomUser(authorities = "CINEMA_ADD_ALL")
    @Test
    public void addCinemaWithPermission() {
        addCinema();
    }

    @Test
    public void getCinema() {
        City city1 = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        City city2 = addCity(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addCinema("cinema1", city1, UUID.fromString("10000000-0000-0000-0000-000000000000"));
        Cinema cinema2 = addCinema("cinema2", city2, UUID.fromString("20000000-0000-0000-0000-000000000000"));

        assertEquals(cinema2, cinemaService.get(cinema2.getId()));
    }

    @Test
    public void getAllCinemas() {
        City city1 = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        City city2 = addCity(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addCinema("cinema1", city1, UUID.fromString("10000000-0000-0000-0000-000000000000"));
        addCinema("cinema2", city2, UUID.fromString("20000000-0000-0000-0000-000000000000"));

        List<Cinema> cinemas = cinemaService.getAll();
        assertEquals(2, cinemas.size());
    }

    @Test
    public void getAllCinemasByCity() {
        City city1 = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        City city2 = addCity(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        addCinema("cinema1", city1, UUID.fromString("10000000-0000-0000-0000-000000000000"));
        addCinema("cinema2", city2, UUID.fromString("20000000-0000-0000-0000-000000000000"));
        addCinema("cinema3", city2, UUID.fromString("20000000-0000-0000-0000-000000000000"));

        List<Cinema> cinemas = cinemaService.getAll(city2);
        assertEquals(2, cinemas.size());
        assertEquals(city2, cinemas.get(0).getCity());
    }

    private void deleteCinema() {
        City city = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema = addCinema("cinema", city, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        int originalSize = cinemaDAO.findAll().size();
        cinemaService.delete(cinema.getId());
        assertNotEquals(originalSize, cinemaDAO.findAll().size());
        assertTrue(cinemaDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteCinemaUnauthenticated() {
        deleteCinema();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteCinemaWithoutPermission() {
        deleteCinema();
    }

    @WithMockCustomUser(authorities = {"CINEMA_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deleteCinemaWithSpecificPermission() {
        deleteCinema();
    }

    @WithMockCustomUser(authorities = {"CINEMA_DELETE_ALL"})
    @Test
    public void deleteCinemaWithGlobalPermission() {
        deleteCinema();
    }

    private void changeName() {
        City city = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema = addCinema("cinema", city, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("cinema", cinemaDAO.find(cinema.getId()).getName());
        cinemaService.changeName(cinema.getId(), "newName");
        assertEquals("newName", cinemaDAO.find(cinema.getId()).getName());
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

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeNameWithSpecificPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_ALL"})
    @Test
    public void changeNameWithGlobalPermission() {
        changeName();
    }

    private void changeCity() {
        City city1 = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        City city2= addCity(UUID.fromString("00000000-0000-0000-0000-000000000002"));
        Cinema cinema = addCinema("cinema", city1, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals(city1, cinemaDAO.find(cinema.getId()).getCity());
        cinemaService.changeCity(cinema.getId(), city2.getId());
        assertEquals(city2, cinemaDAO.find(cinema.getId()).getCity());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeCityUnauthenticated() {
        changeCity();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeCityWithoutPermission() {
        changeCity();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeCityWithSpecificPermission() {
        changeCity();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_ALL"})
    @Test
    public void changeCityWithGlobalPermission() {
        changeCity();
    }

    private void changeAddress() {
        City city = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema = addCinema("cinema", city, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("address", cinemaDAO.find(cinema.getId()).getAddress());
        cinemaService.changeAddress(cinema.getId(), "newAddress");
        assertEquals("newAddress", cinemaDAO.find(cinema.getId()).getAddress());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeAddressUnauthenticated() {
        changeAddress();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeAddressWithoutPermission() {
        changeAddress();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeAddressWithSpecificPermission() {
        changeAddress();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_ALL"})
    @Test
    public void changeAddressWithGlobalPermission() {
        changeAddress();
    }

    private void changePhone() {
        City city = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema = addCinema("cinema", city, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("phone", cinemaDAO.find(cinema.getId()).getPhone());
        cinemaService.changePhone(cinema.getId(), "newPhone");
        assertEquals("newPhone", cinemaDAO.find(cinema.getId()).getPhone());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changePhoneUnauthenticated() {
        changePhone();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changePhoneWithoutPermission() {
        changePhone();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changePhoneWithSpecificPermission() {
        changePhone();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_ALL"})
    @Test
    public void changePhoneWithGlobalPermission() {
        changePhone();
    }

    private void changeWebsite() {
        City city = addCity(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Cinema cinema = addCinema("cinema", city, UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("website", cinemaDAO.find(cinema.getId()).getWebsite());
        cinemaService.changeWebsite(cinema.getId(), "newWebsite");
        assertEquals("newWebsite", cinemaDAO.find(cinema.getId()).getWebsite());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void changeWebsiteUnauthenticated() {
        changeWebsite();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void changeWebsiteWithoutPermission() {
        changeWebsite();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeWebsiteWithSpecificPermission() {
        changeWebsite();
    }

    @WithMockCustomUser(authorities = {"CINEMA_EDIT_ALL"})
    @Test
    public void changeWebsiteWithGlobalPermission() {
        changeWebsite();
    }

    private City addCity(final UUID id) {
        City city = new City("city_" + id.toString());
        city.setId(id);
        cityDAO.add(city);
        return city;
    }

    private Cinema addCinema(String name, City city, UUID id) {
        Cinema cinema = new Cinema(name, "address", "phone", "website");
        cinema.setId(id);
        city.addCinema(cinema);
        cinemaDAO.add(cinema);
        cityDAO.update(city);
        return cinema;
    }
}
