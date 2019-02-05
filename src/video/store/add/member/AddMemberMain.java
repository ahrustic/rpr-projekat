package video.store.add.member;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AddMemberMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addMember.fxml"));
        AddMemberController ctrl = new AddMemberController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Add member");
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
