package video.store.issuelist;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.addissue.AddIssueController;
import video.store.changeissue.ChangeIssueController;
import video.store.classes.Issued;

import java.io.IOException;
import java.net.URL;
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
        listaZaduzenja = FXCollections.observableArrayList(dao.zaduzenja());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(listaZaduzenja);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        clanCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMember().getNaziv()));
        filmCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMovie().getNaziv()));
        zaduzenjeCol.setCellValueFactory(new PropertyValueFactory<>("datumZaduzenja"));
        razduzenjeCol.setCellValueFactory(new PropertyValueFactory<>("datumRazduzenja"));

    }


    public void actionDodajZaduzenje(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addissue/addIssue.fxml"));
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
                    dao.dodajZaduzenje(novoIssued);
                    listaZaduzenja.setAll(dao.zaduzenja());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../changeissue/changeIssue.fxml"));
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
                    dao.promijeniZaduzenje(novoIssued);
                    listaZaduzenja.setAll(dao.zaduzenja());
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
            dao.obrisiZaduzenje(issued);
            listaZaduzenja.setAll(dao.zaduzenja());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }



}
