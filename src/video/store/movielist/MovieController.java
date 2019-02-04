package video.store.movielist;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import video.store.VideotekaDAO;
import video.store.addmember.DodajClanaController;
import video.store.addmovie.DodajFilmController;
import video.store.classes.Clan;
import video.store.classes.Film;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MovieController implements Initializable {
    ObservableList<Film> listaFilmova;

    @FXML
    private StackPane rootPane;
    @FXML
    private TableView<Film> tableView;
    @FXML
    private TableColumn<Film, String> titleCol;
    @FXML
    private TableColumn<Film, Integer> idCol;
    @FXML
    private TableColumn<Film, String> actorCol;
    @FXML
    private TableColumn<Film, Integer> yearCol;
    @FXML
    private TableColumn<Film, Integer> quantityCol;
    @FXML
    private TableColumn<Film, String> genreCol;
    @FXML
    private AnchorPane contentPane;

    private VideotekaDAO dao;

    public MovieController() {
        dao = VideotekaDAO.getInstance();
        listaFilmova = FXCollections.observableArrayList(dao.filmovi());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(listaFilmova);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("zanr"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("godinaIzdanja"));
        actorCol.setCellValueFactory(new PropertyValueFactory<>("glavniGlumac"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("kolicina"));

    }

    public void actionDodajFilm(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmovie/dodajFilm.fxml"));
            DodajFilmController filmController = new DodajFilmController();
            loader.setController(filmController);
            root = loader.load();
            stage.setTitle("Dodaj film");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Film novifilm = filmController.getFilm();
                if (novifilm != null) {
                    dao.promijeniFilm(novifilm);
                    listaFilmova.setAll(dao.filmovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionIzmijeniFilm(ActionEvent actionEvent){
        Film film = tableView.getSelectionModel().getSelectedItem();
        if (film == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmovie/dodajFilm.fxml"));
            DodajFilmController filmController = new DodajFilmController(film, dao.filmovi());
            loader.setController(filmController);
            root = loader.load();
            stage.setTitle("Promijeni film");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Film novifilm = filmController.getFilm();
                if (novifilm != null) {
                    dao.promijeniFilm(novifilm);
                    listaFilmova.setAll(dao.filmovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiFilm(ActionEvent actionEvent) {
        Film film = tableView.getSelectionModel().getSelectedItem();
        if (film == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje film "+ film.getNaziv());
        alert.setContentText("Da li ste sigurni da želite obrisati film " + film.getNaziv()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.obrisiFilm(film);
            listaFilmova.setAll(dao.filmovi());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }



}
