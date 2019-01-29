package video.store.login;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = password.getText();

       /* if (uname.equals(preference.getUsername()) && pword.equals(preference.getPassword())) {
            closeStage();
            loadMain();
        }*/
        //else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
      //  }
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(""));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Film store");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {

        }
    }

}
