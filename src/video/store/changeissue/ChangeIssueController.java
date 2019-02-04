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
import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChangeIssueController implements Initializable {

    @FXML
    private ComboBox<Member> clanCombo = new ComboBox<>();
    @FXML
    private ComboBox<Movie> filmCombo = new ComboBox<>();
    @FXML
    private DatePicker zaduzenjeDate;
    @FXML
    private DatePicker razduzenjeDate;
    @FXML
    private Button dodajBtn;
    @FXML
    private Button cancleBtn;
    private Issued issued;
    private boolean ispravnostZaduzenje = false;
    private boolean ispravnostRazduzenja = false;
    public ObservableList<Member> listaClanova;
    public ObservableList<Movie> listaFilmova;
    public Movie movie;
    public Member member;


    public ChangeIssueController(Issued issued, ArrayList<Movie> f, ArrayList<Member> c){
        this.issued = issued;
        listaClanova = FXCollections.observableArrayList(c);
        listaFilmova = FXCollections.observableArrayList(f);
        clanCombo.setItems(listaClanova);
        filmCombo.setItems(listaFilmova);
    }

    public ChangeIssueController() {
    }


    public void clickCancel(ActionEvent actionEvent) {
        issued = null;
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

        if(issued != null){
            zaduzenjeDate.getEditor().setText(String.valueOf(LocalDate.parse(toString(issued.getDatumZaduzenja()))));
            razduzenjeDate.getEditor().setText(String.valueOf(LocalDate.parse(toString(issued.getDatumRazduzenja()))));
            for (Member c : listaClanova)
                if (c.getId() == issued.getMember().getId()) {
                    clanCombo.getSelectionModel().select(c);
                    clanCombo.setPromptText(String.valueOf(new Member(c.getId(), c.getNaziv(), c.getEmail(), c.getBrojTelefona())));
                }
            for (Movie f : listaFilmova)
                if (f.getId() == issued.getMovie().getId()) {
                    filmCombo.getSelectionModel().select(f);
                    filmCombo.setPromptText(String.valueOf(new Movie(f.getId(), f.getNaziv(), f.getZanr(), f.getGodinaIzdanja(), f.getGlavniGlumac(), f.getKolicina())));
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

    public Issued getIssued() {
        return issued;
    }
}
