package video.store.classes;

public class Movie {

    private int id;
    private String naziv;
    private String zanr;
    private int godnaIzdanja;
    private String glavniGlumac;
    private int kolicina;

    public Movie(int id, String naziv, String zanr, int godnaIzdanja, String glavniGlumac, int kolicina) {
        this.id = id;
        this.naziv = naziv;
        this.zanr = zanr;
        this.godnaIzdanja = godnaIzdanja;
        this.glavniGlumac = glavniGlumac;
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public int getGodnaIzdanja() {
        return godnaIzdanja;
    }

    public void setGodnaIzdanja(int godnaIzdanja) {
        this.godnaIzdanja = godnaIzdanja;
    }

    public String getGlavniGlumac() {
        return glavniGlumac;
    }

    public void setGlavniGlumac(String glavniGlumac) {
        this.glavniGlumac = glavniGlumac;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
