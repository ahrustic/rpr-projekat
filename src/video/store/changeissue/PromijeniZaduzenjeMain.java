package video.store.changeissue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class PromijeniZaduzenjeMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("promijeniZaduzenje.fxml"));
        PromijeniZaduzenjeController ctrl = new PromijeniZaduzenjeController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Promijeni zaduzenje");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
