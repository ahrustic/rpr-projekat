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
import java.nio.file.LinkOption;
import java.sql.Date;
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
    private TableColumn<Issued, String> clanCol;
    @FXML
    private TableColumn<Issued, String> filmCol;
    @FXML
    private TableColumn<Issued, LocalDate> zaduzenjeCol;
    @FXML
    private TableColumn<Issued, LocalDate> razduzenjeCol;

    private VideoStoreDAO dao;

    ObservableList<Issued> listaZaduzenja;

    public IssuedController() {
        dao = VideoStoreDAO.getInstance();
        listaZaduzenja = FXCollections.observableArrayList(dao.issued());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(listaZaduzenja);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        clanCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMember().getName()));
        filmCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMovie().getName()));
        zaduzenjeCol.setCellFactory(column -> {
            TableCell<Issued, LocalDate> cell = new TableCell<Issued, LocalDate>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

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
        razduzenjeCol.setCellFactory(column -> {
            TableCell<Issued, LocalDate> cell = new TableCell<Issued, LocalDate>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

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


    public void actionDodajZaduzenje(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/issue/addIssue.fxml"));
            AddIssueController zaduzenjeController = new AddIssueController();
            loader.setController(zaduzenjeController);
            root = loader.load();
            stage.setTitle("Dodaj zaduzenje");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Issued novoIssued = zaduzenjeController.getIssued();
                if (novoIssued != null) {
                    dao.addIssued(novoIssued);
                    listaZaduzenja.setAll(dao.issued());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPromijeniZaduzenje(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../change/issue/changeIssue.fxml"));
            ChangeIssueController zaduzenjeController = new ChangeIssueController();
            loader.setController(zaduzenjeController);
            root = loader.load();
            stage.setTitle("Promijeni zaduzenje");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Issued novoIssued = zaduzenjeController.getIssued();
                if (novoIssued != null) {
                    dao.changeIssued(novoIssued);
                    listaZaduzenja.setAll(dao.issued());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiZaduzenje(ActionEvent actionEvent) {
        Issued issued = tableView.getSelectionModel().getSelectedItem();
        if (issued == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje issued "+ issued.getId());
        alert.setContentText("Da li ste sigurni da želite obrisati issued " + issued.getId()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteIssued(issued);
            listaZaduzenja.setAll(dao.issued());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }



}
