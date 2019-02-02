package video.store.addmovie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import video.store.login.LoginController;


public class DodajFilmMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dodajFilm.fxml"));
        DodajFilmController ctrl = new DodajFilmController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Dodaj film");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
