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
import video.store.add.member.AddMemberController;
import video.store.classes.Member;
import video.store.classes.Movie;
import video.store.find.FindMovieActorController;

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
    private ObservableList<Member> memberList;

    public MemberListController() {
        dao = VideoStoreDAO.getInstance();
        memberList = FXCollections.observableArrayList(dao.members());
    }

    public MemberListController(Object o, ArrayList<Member> members) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(memberList);
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));

    }

    public void actionAddMember(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/member/addMember.fxml"));
            AddMemberController memberController = new AddMemberController();
            loader.setController(memberController);
            root = loader.load();
            stage.setTitle("Add member");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Member newMember = memberController.getClan();
                if (newMember != null) {
                    dao.addMember(newMember);
                    memberList.setAll(dao.members());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionChangeMember(ActionEvent actionEvent){
        Member member = tableView.getSelectionModel().getSelectedItem();
        if (member == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/member/addMember.fxml"));
            AddMemberController memberController = new AddMemberController(member, dao.members());
            loader.setController(memberController);
            root = loader.load();
            stage.setTitle("Change member");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Member newMember = memberController.getClan();
                if (newMember != null) {
                    dao.changeMember(newMember);
                    memberList.setAll(dao.members());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void actionDeleteMember(ActionEvent actionEvent) {
        Member member = tableView.getSelectionModel().getSelectedItem();
        if (member == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Are you sure that you want delete " + member.getName()+ "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteMember(member);
            memberList.setAll(dao.members());
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }
}
