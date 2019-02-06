package video.store.classes;

public class Movie {

    private int id;
    private String name;
    private String genre;
    private int year;
    private String actor;
    private String production;
    private String quantity;

    public Movie(int id, String name, String genre, int godnaIzdanja, String actor, String production, String quantity) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.year = godnaIzdanja;
        this.actor = actor;
        this.production = production;
        this.quantity = quantity;
    }

    public Movie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
