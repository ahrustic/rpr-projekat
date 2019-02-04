package video.store.issuelist;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import video.store.VideotekaDAO;
import video.store.addissue.DodajZaduzenjeController;
import video.store.addmovie.DodajFilmController;
import video.store.changeissue.PromijeniZaduzenjeController;
import video.store.classes.Clan;
import video.store.classes.Film;
import video.store.classes.Zaduzenje;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class IssuedController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private TableView<Zaduzenje> tableView;
    @FXML
    private TableColumn<Zaduzenje, Integer> idCol;
    @FXML
    private TableColumn<Zaduzenje, String> clanCol;
    @FXML
    private TableColumn<Zaduzenje, String> filmCol;
    @FXML
    private TableColumn<Zaduzenje, LocalDate> zaduzenjeCol;
    @FXML
    private TableColumn<Zaduzenje, LocalDate> razduzenjeCol;

    private VideotekaDAO dao;

    ObservableList<Zaduzenje> listaZaduzenja;

    public IssuedController() {
        dao = VideotekaDAO.getInstance();
        listaZaduzenja = FXCollections.observableArrayList(dao.zaduzenja());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(listaZaduzenja);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        clanCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClan().getNaziv()));
        filmCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFilm().getNaziv()));
        zaduzenjeCol.setCellValueFactory(new PropertyValueFactory<>("datumZaduzenja"));
        razduzenjeCol.setCellValueFactory(new PropertyValueFactory<>("datumRazduzenja"));

    }


    public void actionDodajZaduzenje(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addissue/dodajZaduzenje.fxml"));
            DodajZaduzenjeController zaduzenjeController = new DodajZaduzenjeController();
            loader.setController(zaduzenjeController);
            root = loader.load();
            stage.setTitle("Dodaj zaduzenje");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Zaduzenje novoZaduzenje = zaduzenjeController.getZaduzenje();
                if (novoZaduzenje != null) {
                    dao.dodajZaduzenje(novoZaduzenje);
                    listaZaduzenja.setAll(dao.zaduzenja());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPromijeniZaduzenje(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../changeissue/promijeniZaduzenje.fxml"));
            PromijeniZaduzenjeController zaduzenjeController = new PromijeniZaduzenjeController();
            loader.setController(zaduzenjeController);
            root = loader.load();
            stage.setTitle("Promijeni zaduzenje");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Zaduzenje novoZaduzenje = zaduzenjeController.getZaduzenje();
                if (novoZaduzenje != null) {
                    dao.promijeniZaduzenje(novoZaduzenje);
                    listaZaduzenja.setAll(dao.zaduzenja());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiZaduzenje(ActionEvent actionEvent) {
        Zaduzenje zaduzenje = tableView.getSelectionModel().getSelectedItem();
        if (zaduzenje == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje zaduzenje "+ zaduzenje.getId());
        alert.setContentText("Da li ste sigurni da želite obrisati zaduzenje " + zaduzenje.getId()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.obrisiZaduzenje(zaduzenje);
            listaZaduzenja.setAll(dao.zaduzenja());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }



}
