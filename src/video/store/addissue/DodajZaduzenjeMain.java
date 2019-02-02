package video.store.addissue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import video.store.login.LoginController;


public class DodajZaduzenjeMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dodajZaduzenje.fxml"));
        DodajZaduzenjeController ctrl = new DodajZaduzenjeController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Dodaj zaduzenje");
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
