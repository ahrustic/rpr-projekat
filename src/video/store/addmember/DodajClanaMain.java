package video.store.addmember;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import video.store.login.LoginController;


public class DodajClanaMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dodajClana.fxml"));
        DodajClanaController ctrl = new DodajClanaController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Dodaj clana");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}