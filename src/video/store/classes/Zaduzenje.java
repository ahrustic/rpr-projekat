package video.store.classes;

import java.time.LocalDate;

public class Zaduzenje {

    private int id;
    private Clan clan;
    private Film film;
    private LocalDate datumZaduzenja;
    private LocalDate datumRazduzenja;

    public Zaduzenje(int id, Clan clan, Film film, LocalDate datumZaduzenja, LocalDate datumRazduzenja) {
        this.id = id;
        this.clan = clan;
        this.film = film;
        this.datumZaduzenja = datumZaduzenja;
        this.datumRazduzenja = datumRazduzenja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public LocalDate getDatumZaduzenja() {
        return datumZaduzenja;
    }

    public void setDatumZaduzenja(LocalDate datumZaduzenja) {
        this.datumZaduzenja = datumZaduzenja;
    }

    public LocalDate getDatumRazduzenja() {
        return datumRazduzenja;
    }

    public void setDatumRazduzenja(LocalDate datumRazduzenja) {
        this.datumRazduzenja = datumRazduzenja;
    }
}
