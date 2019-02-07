package video.store;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VideoStoreDAOTest {
    VideoStoreDAO dao = null;

    @Test
    void initDb() {
        if (dao != null) dao.close();

        File dbfile = new File("videostore.db");
        ClassLoader classLoader = getClass().getClassLoader();
        File srcfile = new File(classLoader.getResource("db/videostore.db").getFile());
        try {
            dbfile.delete();
            Files.copy(srcfile.toPath(), dbfile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Ne mogu kreirati bazu");
        }

        dao = new VideoStoreDAO();
    }

    //Potrebno je da pokrenete testove posebno, postoji ovisnost koju nisam uspjela da rijesim
    @Test
    void getMember() {
        initDb();
        ArrayList<Member> members = dao.members();
        assertEquals(1, members.size());
        assertEquals("Mujo Mujic", members.get(0).getName());
        assertEquals("060/123-456", members.get(0).getMobile());
        assertEquals("mujo@live.com", members.get(0).getEmail());
    }

    @Test
    void getMovies() {
        initDb();
        ArrayList<Movie> movies = dao.movies();
        assertEquals(1, movies.size());
        assertEquals("Bumblebee", movies.get(0).getName());
        assertEquals("Akcija", movies.get(0).getGenre());
        assertEquals(2018, movies.get(0).getYear());
        assertEquals("Hailee Steinfeld ", movies.get(0).getActor());
        assertEquals("Marvel", movies.get(0).getProduction());
        assertEquals(5, movies.get(0).getQuantity());
    }

   /* @Test
    void getIssued() {
        initDb();
        ArrayList<Issued> issued = dao.issued();
        assertEquals(1, issued.size());
        assertEquals(1, issued.get(0).getMember().getId());
        assertEquals(1, issued.get(1).getMovie().getId());
    }*/


}


