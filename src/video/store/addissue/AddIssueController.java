package video.store.addissue;

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


public class AddIssueController implements Initializable {


    @FXML
    private ComboBox<Member> clanCombo = new ComboBox<>();
    @FXML
    private ComboBox<Movie> filmCombo = new ComboBox<>();
    @FXML
    private DatePicker zaduzenjeDate;
    @FXML
    private Button dodajBtn;
    @FXML
    private Button cancleBtn;
    private Issued issued;
    private boolean ispravnostZaduzenje = false;
    public ObservableList<Member> listaClanova;
    public ObservableList<Movie> listaFilmova;


    public AddIssueController(Issued issued, ArrayList<Movie> f, ArrayList<Member> c){
        this.issued = issued;
        listaClanova = FXCollections.observableArrayList(c);
        listaFilmova = FXCollections.observableArrayList(f);
        clanCombo.setItems(listaClanova);
        filmCombo.setItems(listaFilmova);
    }

    public AddIssueController() {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clanCombo.setItems(listaClanova);
        filmCombo.setItems(listaFilmova);
        clanCombo.getSelectionModel().selectFirst();
        filmCombo.getSelectionModel().selectFirst();

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

    }

    public Issued getIssued() {
        return issued;
    }
}
