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
    private ComboBox<Member> memberCombo = new ComboBox<>();
    @FXML
    private ComboBox<Movie> movieCombo = new ComboBox<>();
    @FXML
    private DatePicker chargedDate;
    @FXML
    private DatePicker dischargedDate;
    @FXML
    private Button dodajBtn;
    @FXML
    private Button cancleBtn;
    private Issued issued;
    private boolean ispravnostZaduzenje = false;
    private boolean ispravnostRazduzenja = false;
    public ObservableList<Member> memberList;
    public ObservableList<Movie> movieList;
    public Movie movie;
    public Member member;


    public ChangeIssueController(Issued issued, ArrayList<Movie> f, ArrayList<Member> c){
        this.issued = issued;
        memberList = FXCollections.observableArrayList(c);
        movieList = FXCollections.observableArrayList(f);
        memberCombo.setItems(memberList);
        movieCombo.setItems(movieList);
    }

    public ChangeIssueController() {
    }


    public void clickCancel(ActionEvent actionEvent) {
        issued = null;
        Stage stage = (Stage) memberCombo.getScene().getWindow();
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
        LocalDate datumZaduzenja = LocalDate.parse(chargedDate.getEditor().getText(), formatter);
        if (!datum.isAfter(datumZaduzenja)) return false;
        return !datum.isAfter(LocalDate.now());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        memberCombo.setItems(memberList);
        movieCombo.setItems(movieList);
        /*vlasnikCombo.setItems(vlasnici);
        proizvodjacCombo.setItems(proizvodjaci);

        if (vozilo != null) {
            proizvodjacCombo.setValue(vozilo.getProizvodjac().getNaziv());
            modelField.setText(vozilo.getModel());
            brojSasijeField.setText(vozilo.getBrojSasije());
            brojTablicaField.setText(vozilo.getBrojTablica());
            String prezimeIme = vozilo.getVlasnik().getPrezime().concat(" ").concat(vozilo.getVlasnik().getIme());
            vlasnikCombo.setValue(prezimeIme);
        }
    }*/

        if(issued != null){
            chargedDate.getEditor().setText(String.valueOf(LocalDate.parse(toString(issued.getDateCharge()))));
            dischargedDate.getEditor().setText(String.valueOf(LocalDate.parse(toString(issued.getDateDischarge()))));
            for (Member c : memberList)
                if (c.getId() == issued.getMember().getId()) {
                    memberCombo.getSelectionModel().select(c);
                    memberCombo.setPromptText(String.valueOf(new Member(c.getId(), c.getName(), c.getEmail(), c.getMobile())));
                }
            for (Movie f : movieList)
                if (f.getId() == issued.getMovie().getId()) {
                    movieCombo.getSelectionModel().select(f);
                    movieCombo.setPromptText(String.valueOf(new Movie(f.getId(), f.getName(), f.getGenre(), f.getYear(), f.getActor(), f.getProduction(), f.getQuantity())));
                }


        }
        else {
            memberCombo.getSelectionModel().selectFirst();
            movieCombo.getSelectionModel().selectFirst();
        }

        chargedDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (ispravnostDatuma(n)) {
                    chargedDate.getStyleClass().removeAll("notValid");
                    chargedDate.getStyleClass().add("valid");
                    ispravnostZaduzenje = true;
                } else {
                    chargedDate.getStyleClass().removeAll("valid");
                    chargedDate.getStyleClass().add("notValid");
                    ispravnostZaduzenje = false;
                }
            }
        });

        dischargedDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (ispravnostDatumaRazduzenja(n)) {
                    dischargedDate.getStyleClass().removeAll("notValid");
                    dischargedDate.getStyleClass().add("valid");
                    ispravnostRazduzenja = true;
                } else {
                    dischargedDate.getStyleClass().removeAll("valid");
                    dischargedDate.getStyleClass().add("notValid");
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
