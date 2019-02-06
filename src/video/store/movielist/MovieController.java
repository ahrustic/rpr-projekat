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
import video.store.add.movie.AddMovieController;
import video.store.classes.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MovieController implements Initializable {
    ObservableList<Movie> movieList;

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
    private TableColumn<Movie, String> productionCol;
    @FXML
    private AnchorPane contentPane;

    private VideoStoreDAO dao;

    public MovieController() {
        dao = VideoStoreDAO.getInstance();
        movieList = FXCollections.observableArrayList(dao.movies());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(movieList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        actorCol.setCellValueFactory(new PropertyValueFactory<>("actor"));
        productionCol.setCellValueFactory(new PropertyValueFactory<>("production"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    }

    public void actionAddMovie(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/movie/addMovie.fxml"));
            AddMovieController movieController = new AddMovieController();
            loader.setController(movieController);
            root = loader.load();
            stage.setTitle("Add movie");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Movie newMovie = movieController.getMovie();
                if (newMovie != null) {
                    dao.addMovie(newMovie);
                    movieList.setAll(dao.movies());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionChangeMovie(ActionEvent actionEvent){
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        if (movie == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/movie/addMovie.fxml"));
            AddMovieController movieController = new AddMovieController(movie, dao.movies());
            loader.setController(movieController);
            root = loader.load();
            stage.setTitle("Change movie");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Movie newMovie = movieController.getMovie();
                if (newMovie != null) {
                    dao.changeMovie(newMovie);
                    movieList.setAll(dao.movies());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionDeleteMovie(ActionEvent actionEvent) {
        Movie movie = tableView.getSelectionModel().getSelectedItem();
        if (movie == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Are you sure that you want to delete " + movie.getName()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteMovie(movie);
            movieList.setAll(dao.movies());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }



}
