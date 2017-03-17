package movie;

import static org.junit.Assert.*;

/**
 * Created by Incy on 16.03.2017.
 */
public class MovieDAOTest {

    private final Movie[] movies = {new Movie("Logan",110,"No idea"),
                                    new Movie("Inception", 125, "Whatever"),
                                     new Movie("Sherlock",53,"-")};

    private final MovieDAOMongo movieDAOMongo = new MovieDAOMongo();
    private final MovieDAODefault movieDAODefault = new MovieDAODefault();


    @org.junit.Test
    public void testDefault() throws Exception {
        add(movieDAODefault);
        find(movieDAODefault);
        update(movieDAODefault);
        findAll(movieDAODefault);
        delete(movieDAODefault);
    }

    @org.junit.Test
    public void testMongo() throws Exception {
        add(movieDAOMongo);
        find(movieDAOMongo);
        findAll(movieDAOMongo);
        update(movieDAOMongo);
        delete(movieDAOMongo);
    }



    private void add(MovieDAO movieDAO)  {
        for (Movie movie: movies)
        movieDAO.add(movie);
    }
    private void find(MovieDAO movieDAO)
{
    assertEquals(movies[0],movieDAO.find(movies[0].getId()));
}

    private void findAll(MovieDAO movieDAO)
{
    assertEquals(movies.length,movieDAO.findAll().size());
}

    private void update(MovieDAO movieDAO)
{
    movies[2].setName("NewName");
    movieDAO.update(movies[2]);
    assertEquals(movies[2].getName(),movieDAO.find(movies[2].getId()).getName());
}


    private void delete(MovieDAO movieDAO) {
for (Movie movie: movies)
{
    movieDAO.delete(movie.getId());
}
assertNull(movieDAO.find(movies[1].getId()));
    }

}