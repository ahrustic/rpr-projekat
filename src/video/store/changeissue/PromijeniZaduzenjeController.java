package video.store.changeissue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import video.store.classes.Clan;
import video.store.classes.Film;
import video.store.classes.Zaduzenje;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PromijeniZaduzenjeController implements Initializable {

    @FXML
    private ComboBox<Clan> clanCombo = new ComboBox<>();
    @FXML
    private ComboBox<Film> filmCombo = new ComboBox<>();
    @FXML
    private DatePicker zaduzenjeDate;
    @FXML
    private DatePicker razduzenjeDate;
    @FXML
    private Button dodajBtn;
    @FXML
    private Button cancleBtn;
    private Zaduzenje zaduzenje;
    private boolean ispravnostZaduzenje = false;
    private boolean ispravnostRazduzenja = false;
    public ObservableList<Clan> listaClanova;
    public ObservableList<Film> listaFilmova;
    public Film film;
    public Clan clan;


    public PromijeniZaduzenjeController(Zaduzenje zaduzenje, ArrayList<Film> f, ArrayList<Clan> c){
        this.zaduzenje = zaduzenje;
        listaClanova = FXCollections.observableArrayList(c);
        listaFilmova = FXCollections.observableArrayList(f);
        clanCombo.setItems(listaClanova);
        filmCombo.setItems(listaFilmova);
    }

    public PromijeniZaduzenjeController() {
    }


    public void clickCancel(ActionEvent actionEvent) {
        zaduzenje = null;
        Stage stage = (Stage) clanCombo.getScene().getWindow();
        stage.close();
    }

    private boolean ispravnostDatuma(String novi) {
        if (novi.length() != 11) return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate datum = LocalDate.parse(novi, formatter);
        return !datum.isAfter(LocalDate.now());

    }

    private boolean ispravnostDatumaRazduzenja(String novi) {
        if (novi.length() != 11) return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate datum = LocalDate.parse(novi, formatter);
        LocalDate datumZaduzenja = LocalDate.parse(zaduzenjeDate.getEditor().getText(), formatter);
        if (!datum.isAfter(datumZaduzenja)) return false;
        return !datum.isAfter(LocalDate.now());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clanCombo.setItems(listaClanova);
        filmCombo.setItems(listaFilmova);

        if(zaduzenje != null){
            zaduzenjeDate.getEditor().setText(String.valueOf(LocalDate.parse(toString(zaduzenje.getDatumZaduzenja()))));
            razduzenjeDate.getEditor().setText(String.valueOf(LocalDate.parse(toString(zaduzenje.getDatumRazduzenja()))));
            for (Clan c : listaClanova)
                if (c.getId() == zaduzenje.getClan().getId()) {
                    clanCombo.getSelectionModel().select(c);
                    clanCombo.setPromptText(String.valueOf(new Clan(c.getId(), c.getNaziv(), c.getEmail(), c.getBrojTelefona())));
                }
            for (Film f : listaFilmova)
                if (f.getId() == zaduzenje.getFilm().getId()) {
                    filmCombo.getSelectionModel().select(f);
                    filmCombo.setPromptText(String.valueOf(new Film(f.getId(), f.getNaziv(), f.getZanr(), f.getGodinaIzdanja(), f.getGlavniGlumac(), f.getKolicina())));
                }


        }
        else {
            clanCombo.getSelectionModel().selectFirst();
            filmCombo.getSelectionModel().selectFirst();
        }

        zaduzenjeDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (ispravnostDatuma(n)) {
                    zaduzenjeDate.getStyleClass().removeAll("nijeValidan");
                    zaduzenjeDate.getStyleClass().add("validan");
                    ispravnostZaduzenje = true;
                } else {
                    zaduzenjeDate.getStyleClass().removeAll("validan");
                    zaduzenjeDate.getStyleClass().add("nijeValidan");
                    ispravnostZaduzenje = false;
                }
            }
        });

        razduzenjeDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (ispravnostDatumaRazduzenja(n)) {
                    razduzenjeDate.getStyleClass().removeAll("nijeValidan");
                    razduzenjeDate.getStyleClass().add("validan");
                    ispravnostRazduzenja = true;
                } else {
                    razduzenjeDate.getStyleClass().removeAll("validan");
                    razduzenjeDate.getStyleClass().add("nijeValidan");
                    ispravnostRazduzenja = false;
                }
            }
        });

    }

    private String toString(LocalDate datumZaduzenja) {
        return "" + datumZaduzenja;
    }

    public Zaduzenje getZaduzenje() {
        return zaduzenje;
    }
}
