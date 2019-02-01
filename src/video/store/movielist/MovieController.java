package video.store.movielist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import video.store.classes.Film;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {
    ObservableList<Film> list = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("zanr"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("godinaIzdanja"));
        actorCol.setCellValueFactory(new PropertyValueFactory<>("glavniGlumac"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("kolicina"));

    }




}
