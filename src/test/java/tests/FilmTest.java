package tests;

import org.example.films.Film;
import org.junit.jupiter.api.Test;

public class FilmTest {

    @Test
    public void testGetFilm(){

        Film film = Film.builder()
                .name("Troya")
                .annotation("Battle for love")
                .rating(9.1)
                .build();

        System.out.println(film.getName());
    }

}
