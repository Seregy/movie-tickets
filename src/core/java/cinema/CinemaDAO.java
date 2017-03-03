package core.java.cinema;

import java.util.List;

/**
 * Created by Seregy on 02.03.2017.
 */
public interface CinemaDAO {
	Cinema find(int id);
	List<Cinema> find(String name);
	List<Cinema> findAll();
	boolean add(Cinema cinema);
	boolean update(Cinema cinema);
	boolean delete(Cinema cinema);
}
