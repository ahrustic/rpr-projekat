package video.store.classes;

public class Uposlenik {

    private int id;
    private String imePrezime;
    private String korisnickoIme;
    private String sifra;

    public Uposlenik(int id, String imePrezime, String korisnickoIme, String sifra) {
        this.id = id;
        this.imePrezime = imePrezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
}
