package movietickets.city.service;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link CityServiceDAO}.
 *
 * @author Seregy
 */
@WebAppConfiguration
@ActiveProfiles("service-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.ServiceTestConfiguration.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CityServiceDAOTest {
    @Autowired
    private CityService cityService;
    @Autowired
    private CityDAO cityDAO;

    private void addCity() {
        assertEquals(0, cityDAO.findAll().size());

        City city = new City("name");
        cityService.add(city);

        assertEquals(1, cityDAO.findAll().size());
        assertEquals(city.getName(), cityDAO.findAll().get(0).getName());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addCityUnauthenticated() {
        addCity();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void addCityWithoutPermission() {
        addCity();
    }

    @WithMockCustomUser(authorities = "CITY_ADD_ALL")
    @Test
    public void addCityWithPermission() {
        addCity();
    }

    @Test
    public void getCity() {
        createCity("city1", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        City city2 = createCity("city2", UUID.fromString("20000000-0000-0000-0000-000000000000"));

        assertEquals(city2, cityService.get(city2.getId()));
    }

    @Test
    public void getAllCities() {
        createCity("city1", UUID.fromString("10000000-0000-0000-0000-000000000000"));
        createCity("city2", UUID.fromString("20000000-0000-0000-0000-000000000000"));

        List<City> cities = cityService.getAll();
        assertEquals(2, cities.size());
    }

    private void deleteCity() {
        City city = createCity("city", UUID.fromString("10000000-0000-0000-0000-000000000000"));

        int originalSize = cityDAO.findAll().size();
        cityService.delete(city.getId());
        assertNotEquals(originalSize, cityDAO.findAll().size());
        assertTrue(cityDAO.findAll().isEmpty());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteCityUnauthenticated() {
        deleteCity();
    }

    @WithMockCustomUser
    @Test(expected = AccessDeniedException.class)
    public void deleteCityWithoutPermission() {
        deleteCity();
    }

    @WithMockCustomUser(authorities = {"CITY_DELETE_10000000-0000-0000-0000-000000000000"})
    @Test
    public void deleteCityWithSpecificPermission() {
        deleteCity();
    }

    @WithMockCustomUser(authorities = {"CITY_DELETE_ALL"})
    @Test
    public void deleteCityWithGlobalPermission() {
        deleteCity();
    }

    private void changeName() {
        City city = createCity("city", UUID.fromString("10000000-0000-0000-0000-000000000000"));

        assertEquals("city", cityDAO.find(city.getId()).getName());
        cityService.changeName(city.getId(), "newName");
        assertEquals("newName", cityDAO.find(city.getId()).getName());
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

    @WithMockCustomUser(authorities = {"CITY_EDIT_10000000-0000-0000-0000-000000000000"})
    @Test
    public void changeNameWithSpecificPermission() {
        changeName();
    }

    @WithMockCustomUser(authorities = {"CITY_EDIT_ALL"})
    @Test
    public void changeNameWithGlobalPermission() {
        changeName();
    }

    private City createCity(final String name,
                            final UUID id) {
        City city = new City(name);
        city.setId(id);
        cityDAO.add(city);
        return city;
    }
}
