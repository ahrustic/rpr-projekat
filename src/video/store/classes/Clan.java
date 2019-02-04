package video.store.classes;

public class Clan {
   private int id;
   private String naziv;
   private String brojTelefona;
   private String email;

    public Clan(int id, String name, String mobile, String email) {
        this.id = id;
        this.naziv = name;
        this.brojTelefona = mobile;
        this.email = email;
    }

    public Clan() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }
    
    
}
