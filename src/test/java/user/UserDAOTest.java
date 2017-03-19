package user;

import user.dao.UserDAO;
import user.dao.UserDAODefault;
import user.dao.UserDAOMongo;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for UserDAODefault and UserDAOMongo.
 */
public class UserDAOTest {
    private final User[] users = {
            new User(UUID.randomUUID(), "Full name", "Nickname",
                    "pass", "salt", "email"),
            new User(UUID.randomUUID(), "Full name 2", "Nickname 2",
                    "pass", "salt", "email"),
            new User(UUID.randomUUID(), "Full name 3", "Nickname 3",
                    "pass", "salt", "email"),
            new User(UUID.randomUUID(), "Full name 4", "Nickname 4",
                    "pass", "salt", "email")};

    private UserDAO daoDefault = new UserDAODefault();
    private UserDAO daoMongo = new UserDAOMongo();

    @org.junit.Test
    public void testDefault() throws Exception {
        add(daoDefault);
        find(daoDefault);
        update(daoDefault);
        findAll(daoDefault);
        delete(daoDefault);
    }

    @org.junit.Test
    public void testMongo() throws Exception {
        add(daoMongo);
        find(daoMongo);
        findAll(daoMongo);
        update(daoMongo);
        delete(daoMongo);
    }

    private void find(UserDAO userDAO) {
        assertEquals(users[2], userDAO.find(users[2].getId()));
    }

    private void findAll(UserDAO userDAO) {
        assertEquals(users.length, userDAO.findAll().size());
    }

    private void add(UserDAO userDAO) {
        for (User user : users) {
            userDAO.add(user);
        }
    }

    private void update(UserDAO userDAO) {
        userDAO.update(new User(users[2].getId(), "Full name changed", "",
                "", "", ""));
        assertNotEquals(users[2].getFullName(), userDAO.find(users[2].getId()).getFullName());
        assertNotEquals(users[2].getEmail(), userDAO.find(users[2].getId()).getEmail());
    }

    private void delete(UserDAO userDAO) {
        for (User user : users) {
            userDAO.delete(user.getId());
        }
    }

}
