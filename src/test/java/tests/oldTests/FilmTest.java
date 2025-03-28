package tests.oldTests;

import org.example.oldFile.films.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("FilmTest")
public class FilmTest {

    @Test
    public void testGetFilm(){

        Film film = Film.builder()
                .name("Troya")
                .annotation("Battle for love")
                .rating(9.1)
                .build();

        System.out.println(film.getName());

        Assertions.assertTrue(film.getName() == "Troya");

    }

}
