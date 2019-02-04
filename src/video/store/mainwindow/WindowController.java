package video.store.mainwindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.addissue.AddIssueController;
import video.store.addmember.AddMemberController;
import video.store.addmovie.AddMovieController;
import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;
import video.store.issuelist.IssuedController;
import video.store.memberlist.MemberListController;
import video.store.movielist.MovieController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class WindowController implements Initializable {

    private VideoStoreDAO dao;
    private ObservableList<Member> listaClanova;
    private ObservableList<Movie> listaFilmova;
    private ObservableList<Issued> listaZaduzenja;
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = VideoStoreDAO.getInstance();
        listaClanova = FXCollections.observableArrayList(dao.clanovi());
        listaFilmova = FXCollections.observableArrayList(dao.filmovi());
        listaZaduzenja = FXCollections.observableArrayList(dao.zaduzenja());
    }

    public void actionDodajClan(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmember/addMember.fxml"));
            AddMemberController clanController = new AddMemberController();
            loader.setController(clanController);
            root = loader.load();
            stage.setTitle("Dodaj clana");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Member noviclan = AddMemberController.getClan();
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

    public void actionDodajZaduzenje(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addissue/addIssue.fxml"));
            AddIssueController zaduzenjeController = new AddIssueController();
            loader.setController(zaduzenjeController);
            root = loader.load();
            stage.setTitle("Dodaj zaduzenje");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Issued novoIssued = zaduzenjeController.getIssued();
                if (novoIssued != null) {
                    dao.dodajZaduzenje(novoIssued);
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
