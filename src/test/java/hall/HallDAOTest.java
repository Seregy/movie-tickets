package hall;


import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Incy on 16.03.2017.
 */
public class HallDAOTest {

    private final Hall[] halls = {new Hall("Red", UUID.randomUUID()),
            new Hall("White", UUID.randomUUID()),
            new Hall("Black",UUID.randomUUID())};

    private final HallDAOMongo hallDAOMongo = new HallDAOMongo();
    private final HallDAODefault hallDAODefault = new HallDAODefault();


    @org.junit.Test
    public void testDefault() throws Exception {
        add(hallDAODefault);
        find(hallDAODefault);
        update(hallDAODefault);
        findAll(hallDAODefault);
        delete(hallDAODefault);
    }

    @org.junit.Test
    public void testMongo() throws Exception {
        add(hallDAOMongo);
        find(hallDAOMongo);
        findAll(hallDAOMongo);
        update(hallDAOMongo);
        delete(hallDAOMongo);
    }
    private void add(HallDAO hallDAO)  {
        for (Hall hall: halls)
            hallDAO.add(hall);
    }
    private void find(HallDAO hallDAO)
    {
        assertEquals(halls[0],hallDAO.find(halls[0].getId()));
    }

    private void findAll(HallDAO hallDAO)
    {
        assertEquals(halls.length,hallDAO.findAll().size());
    }

    private void update(HallDAO hallDAO)
    {
        halls[2].setName("NewName");
        hallDAO.update(halls[2]);
        assertEquals(halls[2].getName(),hallDAO.find(halls[2].getId()).getName());
    }


    private void delete(HallDAO hallDAO) {
        for (Hall hall: halls)
        {
            hallDAO.delete(hall.getId());
        }
        assertNull(hallDAO.find(halls[1].getId()));
    }
}