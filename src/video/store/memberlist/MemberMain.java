package video.store.memberlist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MemberMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("member.fxml"));
        MemberListController ctrl = new MemberListController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Members");
        primaryStage.setScene(new Scene(root, 722, 456));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

