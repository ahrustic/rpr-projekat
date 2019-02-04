package video.store.addmovie;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import video.store.classes.Clan;
import video.store.classes.Film;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DodajFilmController implements Initializable {

    public TextField titleField;
    public TextField zanrField;
    public TextField yearField;
    public TextField actorField;
    public TextField kolicinaField;
    private boolean ispravnostNaziva;
    private boolean ispravnostZanra;
    private boolean ispravnostGodine;
    private boolean ispravnostGlumca;
    private boolean ispravnostKolicine;
    public Film film;
    private boolean sveOk = true;

    public DodajFilmController(){}

    public DodajFilmController(Film film, ArrayList<Film> filmovi) {
        this.film = film;
    }

    public  Film getFilm() {
        return film;
    }

    public void clickCancel(ActionEvent actionEvent) {
        film = null;
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private boolean ispravnost(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z') && n.charAt(i) != ' ')
                return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean ispravnostKolicine(String n){
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= '0' && n.charAt(i) <= '9'))
                return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean ispravnostGodine(String n){
        if (n.length() != 4) return false;
        String trenutnaGodina = "";
        LocalDate godina = LocalDate.now();
        trenutnaGodina += godina.getYear();
        return !trenutnaGodina.equals(n);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (film != null) {
            titleField.setText(film.getNaziv());
            zanrField.setText(film.getZanr());
            yearField.setText(Integer.toString(film.getGodinaIzdanja()));
            actorField.setText(film.getGlavniGlumac());
            kolicinaField.setText(Integer.toString(film.getKolicina()));
        }

        ispravnostNaziva = false;
        ispravnostZanra = false;
        ispravnostGlumca = false;
        ispravnostGodine = false;
        ispravnostKolicine = false;
        titleField.getStyleClass().add("nijeValidan");
        zanrField.getStyleClass().add("nijeValidan");
        yearField.getStyleClass().add("nijeValidan");
        actorField.getStyleClass().add("nijeValidan");
        kolicinaField.getStyleClass().add("nijeValidan");

        titleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    titleField.getStyleClass().removeAll("nijeValidan");
                    titleField.getStyleClass().add("validan");
                    ispravnostNaziva = true;
                }
                else {
                    titleField.getStyleClass().removeAll("validan");
                    titleField.getStyleClass().add("nijeValidan");
                    ispravnostNaziva = false;
                    sveOk = false;
                }
            }
        });

        zanrField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    zanrField.getStyleClass().removeAll("nijeValidan");
                    zanrField.getStyleClass().add("validan");
                    ispravnostZanra = true;
                }
                else {
                    zanrField.getStyleClass().removeAll("validan");
                    zanrField.getStyleClass().add("nijeValidan");
                    ispravnostZanra = false;
                    sveOk = false;
                }
            }
        });

        yearField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnostGodine(n)) {
                    yearField.getStyleClass().removeAll("nijeValidan");
                    yearField.getStyleClass().add("validan");
                    ispravnostGodine = true;
                }
                else {
                    yearField.getStyleClass().removeAll("validan");
                    yearField.getStyleClass().add("nijeValidan");
                    ispravnostGodine = false;
                    sveOk = false;
                }
            }
        });

        actorField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    actorField.getStyleClass().removeAll("nijeValidan");
                    actorField.getStyleClass().add("validan");
                    ispravnostGlumca = true;
                }
                else {
                    actorField.getStyleClass().removeAll("validan");
                    actorField.getStyleClass().add("nijeValidan");
                    ispravnostGlumca = false;
                    sveOk = false;
                }
            }
        });

        kolicinaField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnostKolicine(n)) {
                    kolicinaField.getStyleClass().removeAll("nijeValidan");
                    kolicinaField.getStyleClass().add("validan");
                    ispravnostKolicine = true;
                }
                else {
                    kolicinaField.getStyleClass().removeAll("validan");
                    kolicinaField.getStyleClass().add("nijeValidan");
                    ispravnostKolicine = false;
                    sveOk = false;
                }
            }
        });
    }

    public void uredu(javafx.event.ActionEvent actionEvent){
        if (!sveOk) return;

        if (film == null) film = new Film();
        film.setNaziv(titleField.getText());
        film.setZanr((zanrField.getText()));
        film.setGodinaIzdanja(Integer.parseInt(yearField.getText()));
        film.setGlavniGlumac(actorField.getText());
        film.setKolicina(Integer.parseInt(kolicinaField.getText()));
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

}
