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
import video.store.about.AboutController;
import video.store.add.issue.AddIssueController;
import video.store.add.member.AddMemberController;
import video.store.add.movie.AddMovieController;
import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;
import video.store.issuedlist.IssuedController;
import video.store.memberlist.MemberListController;
import video.store.movielist.MovieController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class WindowController implements Initializable {

    private VideoStoreDAO dao;
    private ObservableList<Member> memberList;
    private ObservableList<Movie> movieList;
    private ObservableList<Issued> issuedList;
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = VideoStoreDAO.getInstance();
        memberList = FXCollections.observableArrayList(dao.members());
        movieList = FXCollections.observableArrayList(dao.movies());
        issuedList = FXCollections.observableArrayList(dao.issued());
    }

    public void actionAddMember(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/member/addMember.fxml"));
            AddMemberController memberController = new AddMemberController();
            loader.setController(memberController);
            root = loader.load();
            stage.setTitle("Add member");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Member noviclan = memberController.getClan();
                if (noviclan != null) {
                    dao.addMember(noviclan);
                    memberList.setAll(dao.members());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                Movie novifilm = movieController.getMovie();
                if (novifilm != null) {
                    dao.addMovie(novifilm);
                    movieList.setAll(dao.movies());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionAddIssued(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/issue/addIssue.fxml"));
            AddIssueController issuedController = new AddIssueController();
            loader.setController(issuedController);
            root = loader.load();
            stage.setTitle("Add issued");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Issued novoIssued = issuedController.getIssued();
                if (novoIssued != null) {
                    dao.addIssued(novoIssued);
                    issuedList.setAll(dao.issued());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listMember(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../memberlist/member.fxml"));
        MemberListController ctrl = new MemberListController();
        loader.setController(ctrl);
        try {
            root = loader.load();
            stage.setTitle("Members");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listMovie(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../movielist/movie.fxml"));
        MovieController ctrl = new MovieController();
        loader.setController(ctrl);
        try {
            root = loader.load();
            stage.setTitle("Movies");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listIssued(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../issuedlist/issued.fxml"));
        IssuedController ctrl = new IssuedController();
        loader.setController(ctrl);
        try {
            root = loader.load();
            stage.setTitle("Issued");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void about(ActionEvent actionEvent){
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../about/about.fxml"));
            AboutController ctrl = new AboutController();
            loader.setController(ctrl);
            try {
                root = loader.load();
                stage.setTitle("About");
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
