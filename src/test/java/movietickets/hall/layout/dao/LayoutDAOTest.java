package movietickets.hall.layout.dao;

import movietickets.hall.layout.Layout;
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
 * Tests for Layout data access object.
 *
 * @author Seregy
 */
@WebAppConfiguration
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = movietickets.core.TestConfiguration.class)
@Transactional
public class LayoutDAOTest {
    @Autowired
    private LayoutDAO layoutDAO;

    @Test
    public void findLayout() {
        Layout[] layouts = {
                new Layout(1, 1),
                new Layout(2, 2),
                new Layout(3, 3)};
        for (Layout layout : layouts) {
            layoutDAO.add(layout);
        }

        assertEquals(layouts[1], layoutDAO.find(layouts[1].getId()));
    }

    @Test
    public void findAllLayouts() {
        Layout[] layouts = {
                new Layout(1, 1),
                new Layout(2, 2),
                new Layout(3, 3),
                new Layout(4, 4)};
        for (Layout layout : layouts) {
            layoutDAO.add(layout);
        }

        assertArrayEquals(layouts, layoutDAO.findAll().toArray(new Layout[0]));
    }

    @Test
    public void addLayout() {
        Layout layout = new Layout(1, 1);

        assertTrue(layoutDAO.add(layout));
        assertEquals(layout, layoutDAO.find(layout.getId()));
    }

    @Test
    public void updateLayout() {
        Layout layout = new Layout(1, 1);
        layoutDAO.add(layout);
        layout.setRowsAmount(10);

        assertTrue(layoutDAO.update(layout));
        assertEquals(layout.getRowsAmount(),
                layoutDAO.find(layout.getId()).getRowsAmount());
    }

    @Test
    public void deleteLayout() {
        Layout layout = new Layout(1, 1);
        layoutDAO.add(layout);

        assertTrue(layoutDAO.delete(layout.getId()));
        assertNull(layoutDAO.find(layout.getId()));
    }
}
