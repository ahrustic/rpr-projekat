package video.store.classes;

public class Zaduzenja {

    private int id;
    private Clan clan;
    private Film film;

    public Zaduzenja(int id, Clan clan, Film film) {
        this.id = id;
        this.clan = clan;
        this.film = film;
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
}
