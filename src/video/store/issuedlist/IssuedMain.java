package video.store.issuedlist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IssuedMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("issued.fxml"));
        IssuedController ctrl = new IssuedController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Issued");
        primaryStage.setScene(new Scene(root, 722, 456));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
