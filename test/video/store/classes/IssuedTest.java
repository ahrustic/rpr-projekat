package video.store.classes;

import org.junit.jupiter.api.Test;
import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IssuedTest {

    @org.junit.jupiter.api.Test
    void getId() {
            Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
            assertDoesNotThrow(
                    () -> issued.getId()
            );

    }

    @org.junit.jupiter.api.Test
    void setId() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.setId(2)
        );
    }

    @org.junit.jupiter.api.Test
    void getMember() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.getMember()
        );
    }

    @org.junit.jupiter.api.Test
    void setMember() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.setMember(new Member(5,"Fata Mujic", "063/879-658", "fata@gmail.com"))
        );
    }

    @org.junit.jupiter.api.Test
    void getMovie() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.getMovie()
        );
    }

    @org.junit.jupiter.api.Test
    void setMovie() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.setMovie(new Movie(1, "Fast and Furious", "Akcija",2005, "Paul Walker", "Universal Pictures",9))
        );
    }

    @org.junit.jupiter.api.Test
    void getDateCharge() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.getDateCharge()
        );
    }

    @org.junit.jupiter.api.Test
    void setDateCharge() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.setDateCharge(LocalDate.now())
        );
    }

    @org.junit.jupiter.api.Test
    void getDateDischarge() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.getDateDischarge()
        );
    }

    @org.junit.jupiter.api.Test
    void setDateDischarge() {
        Issued issued = new Issued(1 , new Member(1, "Mujo Mujic", "060/123-456", "mujo@live.com"), new Movie(1, "Bumblebee", "Akcija",2018, "Hailee Steinfeld ", "Marvel",5), LocalDate.now(), null );
        assertDoesNotThrow(
                () -> issued.setDateDischarge(LocalDate.now())
        );
    }
}