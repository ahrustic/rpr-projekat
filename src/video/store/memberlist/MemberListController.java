package video.store.memberlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.addmember.AddMemberController;
import video.store.classes.Member;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MemberListController implements Initializable {

    public TableView<Member> tableView;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn mobileCol;
    public TableColumn emailCol;
    private VideoStoreDAO dao;
    private ObservableList<Member> listaClanova;

    public MemberListController() {
        dao = VideoStoreDAO.getInstance();
        listaClanova = FXCollections.observableArrayList(dao.clanovi());
    }

    public MemberListController(Object o, ArrayList<Member> clanovi) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(listaClanova);
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory("naziv"));
        mobileCol.setCellValueFactory(new PropertyValueFactory("email"));
        emailCol.setCellValueFactory(new PropertyValueFactory("brojTelefona"));

    }

    public void actionDodajClan(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmember/addMember.fxml"));
            AddMemberController clanController = new AddMemberController();
            loader.setController(clanController);
            root = loader.load();
            stage.setTitle("Dodaj clana");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Member noviclan = AddMemberController.getClan();
                if (noviclan != null) {
                    dao.dodajClan(noviclan);
                    listaClanova.setAll(dao.clanovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionIzmijeniClan(ActionEvent actionEvent){
        Member member = tableView.getSelectionModel().getSelectedItem();
        if (member == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../addmember/addMember.fxml"));
            AddMemberController clanController = new AddMemberController(member, dao.clanovi());
            loader.setController(clanController);
            root = loader.load();
            stage.setTitle("Promijeni clana");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Member noviclan = AddMemberController.getClan();
                if (noviclan != null) {
                    dao.promijeniClana(noviclan);
                    listaClanova.setAll(dao.clanovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiClan(ActionEvent actionEvent) {
        Member member = tableView.getSelectionModel().getSelectedItem();
        if (member == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje clana "+ member.getNaziv());
        alert.setContentText("Da li ste sigurni da želite obrisati clana " + member.getNaziv()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.obrisiClan(member);
            listaClanova.setAll(dao.clanovi());
        }
    }
    public void resetujBazu() {
        VideoStoreDAO.removeInstance();
        File dbfile = new File("videostore.db");
        dbfile.delete();
        dao = VideoStoreDAO.getInstance();
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }
}
