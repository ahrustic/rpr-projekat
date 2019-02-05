package video.store.about;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AboutMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        AboutController ctrl = new AboutController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("About");
        primaryStage.setScene(new Scene(root, 260, 320));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

