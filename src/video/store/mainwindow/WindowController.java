package video.store.mainwindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import video.store.VideotekaDAO;
import video.store.addissue.DodajZaduzenjeController;
import video.store.addmember.DodajClanaController;
import video.store.addmovie.DodajFilmController;
import video.store.classes.Clan;
import video.store.classes.Film;
import video.store.classes.Zaduzenje;
import video.store.issuelist.IssuedController;
import video.store.memberlist.MemberListController;
import video.store.movielist.MovieController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class WindowController implements Initializable {

    private VideotekaDAO dao;
    private ObservableList<Clan> listaClanova;
    private ObservableList<Film> listaFilmova;
    private ObservableList<Zaduzenje> listaZaduzenja;
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = VideotekaDAO.getInstance();
        listaClanova = FXCollections.observableArrayList(dao.clanovi());
        listaFilmova = FXCollections.observableArrayList(dao.filmovi());
        listaZaduzenja = FXCollections.observableArrayList(dao.zaduzenja());
    }

    public void actionDodajClan(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmember/dodajClana.fxml"));
            DodajClanaController clanController = new DodajClanaController();
            loader.setController(clanController);
            root = loader.load();
            stage.setTitle("Dodaj clana");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Clan noviclan = DodajClanaController.getClan();
                if (noviclan != null) {
                    dao.dodajClan(noviclan);
                    listaClanova.setAll(dao.clanovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    dao.dodajFilm(novifilm);
                    listaFilmova.setAll(dao.filmovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void listaClanova(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../memberlist/member.fxml"));
        MemberListController ctrl = new MemberListController();
        loader.setController(ctrl);
        try {
            root = loader.load();
            stage.setTitle("Clanovi");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listaFilmova(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../movielist/movie.fxml"));
        MovieController ctrl = new MovieController();
        loader.setController(ctrl);
        try {
            root = loader.load();
            stage.setTitle("Filmovi");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listaZaduzenja(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../issuelist/issued.fxml"));
        IssuedController ctrl = new IssuedController();
        loader.setController(ctrl);
        try {
            root = loader.load();
            stage.setTitle("Zaduzenja");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clickCancel(ActionEvent actionEvent) {
        Platform.exit();
    }
}
