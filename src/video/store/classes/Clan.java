package video.store.classes;

public class Clan {
   private String id;
   private String naziv;
   private String email;
   private String brojTelefona;

    public Clan(String id, String name, String email, String mobile) {
        this.id = id;
        this.naziv = name;
        this.email = email;
        this.brojTelefona = mobile;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
