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
import video.store.VideoStoreDAO;
import video.store.addmovie.AddMovieController;
import video.store.classes.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MovieController implements Initializable {
    ObservableList<Movie> listaFilmova;

    @FXML
    private StackPane rootPane;
    @FXML
    private TableView<Movie> tableView;
    @FXML
    private TableColumn<Movie, String> titleCol;
    @FXML
    private TableColumn<Movie, Integer> idCol;
    @FXML
    private TableColumn<Movie, String> actorCol;
    @FXML
    private TableColumn<Movie, Integer> yearCol;
    @FXML
    private TableColumn<Movie, Integer> quantityCol;
    @FXML
    private TableColumn<Movie, String> genreCol;
    @FXML
    private AnchorPane contentPane;

    private VideoStoreDAO dao;

    public MovieController() {
        dao = VideoStoreDAO.getInstance();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmovie/addMovie.fxml"));
            AddMovieController filmController = new AddMovieController();
            loader.setController(filmController);
            root = loader.load();
            stage.setTitle("Dodaj movie");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Movie novifilm = filmController.getMovie();
                if (novifilm != null) {
                    dao.dodajFilm(novifilm);
                    listaFilmova.setAll(dao.filmovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionIzmijeniFilm(ActionEvent actionEvent){
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        if (movie == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmovie/addMovie.fxml"));
            AddMovieController filmController = new AddMovieController(movie, dao.filmovi());
            loader.setController(filmController);
            root = loader.load();
            stage.setTitle("Promijeni movie");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Movie novifilm = filmController.getMovie();
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
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        if (movie == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje movie "+ movie.getNaziv());
        alert.setContentText("Da li ste sigurni da želite obrisati movie " + movie.getNaziv()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.obrisiFilm(movie);
            listaFilmova.setAll(dao.filmovi());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }



}
