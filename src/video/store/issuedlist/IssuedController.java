package video.store.issuedlist;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.add.issue.AddIssueController;
import video.store.changeissue.ChangeIssueController;
import video.store.classes.Issued;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class IssuedController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private TableView<Issued> tableView;
    @FXML
    private TableColumn<Issued, Integer> idCol;
    @FXML
    private TableColumn<Issued, String> memberCol;
    @FXML
    private TableColumn<Issued, String> movieCol;
    @FXML
    private TableColumn<Issued, LocalDate> dateChargeCol;
    @FXML
    private TableColumn<Issued, LocalDate> dateDischargeCol;

    private VideoStoreDAO dao;

    ObservableList<Issued> issuedList;

    public IssuedController() {
        dao = VideoStoreDAO.getInstance();
        issuedList = FXCollections.observableArrayList(dao.issued());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(issuedList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMember().getName()));
        movieCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMovie().getName()));
        dateChargeCol.setCellFactory(column -> {
            TableCell<Issued, LocalDate> cell = new TableCell<Issued, LocalDate>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.");

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        dateDischargeCol.setCellFactory(column -> {
            TableCell<Issued, LocalDate> cell = new TableCell<Issued, LocalDate>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.");

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });

    }


    public void actionAdd(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/issue/addIssue.fxml"));
            AddIssueController issueController = new AddIssueController();
            loader.setController(issueController);
            root = loader.load();
            stage.setTitle("Add issue");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Issued newIssued = issueController.getIssued();
                if (newIssued != null) {
                    dao.addIssued(newIssued);
                    issuedList.setAll(dao.issued());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionChange(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../change/issue/changeIssue.fxml"));
            ChangeIssueController changeController = new ChangeIssueController();
            loader.setController(changeController);
            root = loader.load();
            stage.setTitle("Change issued");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Issued newIssued = changeController.getIssued();
                if (newIssued != null) {
                    dao.changeIssued(newIssued);
                    issuedList.setAll(dao.issued());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionDelete(ActionEvent actionEvent) {
        Issued issued = tableView.getSelectionModel().getSelectedItem();
        if (issued == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Are you sure that you want to delete" + issued.getId()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteIssued(issued);
            issuedList.setAll(dao.issued());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }



}
