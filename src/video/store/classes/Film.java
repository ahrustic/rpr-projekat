package video.store.classes;

public class Film {

    private int id;
    private String naziv;
    private String zanr;
    private int godinaIzdanja;
    private String glavniGlumac;
    private int kolicina;

    public Film(int id, String naziv, String zanr, int godnaIzdanja, String glavniGlumac, int kolicina) {
        this.id = id;
        this.naziv = naziv;
        this.zanr = zanr;
        this.godinaIzdanja = godnaIzdanja;
        this.glavniGlumac = glavniGlumac;
        this.kolicina = kolicina;
    }

    public Film() {

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

    public int getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public void setGodinaIzdanja(int godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
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
