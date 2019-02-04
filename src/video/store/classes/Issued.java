package video.store.classes;

import java.time.LocalDate;

public class Issued {

    private int id;
    private Member member;
    private Movie movie;
    private LocalDate datumZaduzenja;
    private LocalDate datumRazduzenja;

    public Issued(int id, Member member, Movie movie, LocalDate datumZaduzenja, LocalDate datumRazduzenja) {
        this.id = id;
        this.member = member;
        this.movie = movie;
        this.datumZaduzenja = datumZaduzenja;
        this.datumRazduzenja = datumRazduzenja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
