package video.store.classes;

import java.time.LocalDate;

public class Issued {

    private int id;
    private Member member;
    private Movie movie;
    private LocalDate dateCharge = LocalDate.now();
    private LocalDate dateDischarge = LocalDate.now();

    public Issued(int id, Member member, Movie movie, LocalDate dateCharge, LocalDate dateDischarge) {
        this.id = id;
        this.member = member;
        this.movie = movie;
        this.dateCharge = dateCharge;
        this.dateDischarge = dateDischarge;
    }

    public Issued(){
        dateCharge = LocalDate.now();
        dateDischarge = LocalDate.now();
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

    public LocalDate getDateCharge() {
        return dateCharge;
    }

    public void setDateCharge(LocalDate dateCharge) {
        this.dateCharge = dateCharge;
    }

    public LocalDate getDateDischarge() {
        return dateDischarge;
    }

    public void setDateDischarge(LocalDate dateDischarge) {
        this.dateDischarge = dateDischarge;
    }
}
