package video.store.add.issue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddIssueController implements Initializable {


    @FXML
    private ComboBox<Member> memberCombo = new ComboBox<>();
    @FXML
    private ComboBox<Movie> movieCombo = new ComboBox<>();
    @FXML
    private DatePicker chargeDate;
    private Issued issued;
    private boolean boolIssued = false;
    public ObservableList<Member> memberList;
    public ObservableList<Movie> movieList;
    public VideoStoreDAO dao;


    public AddIssueController(Issued issued, ArrayList<Movie> f, ArrayList<Member> c) {
        this.issued = issued;
        memberList = FXCollections.observableArrayList(c);
        movieList = FXCollections.observableArrayList(f);
        memberCombo.setItems(memberList);
        movieCombo.setItems(movieList);
    }

    public AddIssueController() {
        dao = new VideoStoreDAO();
        memberList = FXCollections.observableArrayList(dao.members());
        movieList = FXCollections.observableArrayList(dao.movies());
        memberCombo.setItems(memberList);
        movieCombo.setItems(movieList);
    }


    public void clickCancel(ActionEvent actionEvent) {
        issued = null;
        Stage stage = (Stage) memberCombo.getScene().getWindow();
        stage.close();
    }

    private boolean boolDate(String novi) {
        if (novi.length() != 11) return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate datum = LocalDate.parse(novi, formatter);
        return !datum.isAfter(LocalDate.now());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        memberCombo.setItems(memberList);
        movieCombo.setItems(movieList);
        if (issued != null) {
            for (Movie p : movieList)
                if (p.getId() == issued.getMovie().getId()) {
                    movieCombo.getSelectionModel().select(p);
                    movieCombo.setPromptText(new Movie(p.getId(), p.getName(), p.getGenre(), p.getYear(), p.getActor(), p.getProduction(), p.getQuantity()).toString());
                }
            for (Member v : memberList)
                if (v.getId() == issued.getMember().getId()) {
                    memberCombo.getSelectionModel().select(v);
                    memberCombo.setPromptText(new Member(v.getId(), v.getName(), v.getMobile(), v.getEmail()).toString());
                }
        } else {
            chargeDate.getEditor().setText(String.valueOf(LocalDate.now()));
            memberCombo.getSelectionModel().selectFirst();
            movieCombo.getSelectionModel().selectFirst();
        }

        chargeDate.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (boolDate(n)) {
                    chargeDate.getStyleClass().removeAll("notValid");
                    chargeDate.getStyleClass().add("valid");
                    boolIssued = true;
                } else {
                    chargeDate.getStyleClass().removeAll("valid");
                    chargeDate.getStyleClass().add("notValid");
                    boolIssued = false;
                }
            }
        });

    }

    public void okClick(ActionEvent actionEvent) {

        if(issued == null){
          issued = new Issued(0, memberCombo.getValue(), movieCombo.getValue(), chargeDate.getValue(), null);
        dao.addIssued(issued);

    } else

    {
        issued.setMember(memberCombo.getValue());
        issued.setMovie(movieCombo.getValue());
        issued.setDateCharge(chargeDate.getValue());
        issued.setDateDischarge(null);
        dao.changeIssued(issued);
    }

        Stage stage = (Stage) memberCombo.getScene().getWindow();
        stage.close();
}
    public Issued getIssued() {
        return issued;
    }
}
