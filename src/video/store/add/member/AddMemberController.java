package video.store.add.member;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.classes.Member;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMemberController implements Initializable {

    public TextField nameField;
    public TextField mobileField;
    public TextField emailField;
    private boolean validName;
    private boolean validMobile;
    private boolean validEmail;
    private Member member;

    private ArrayList<Member> members;
    private boolean OK;
    public VideoStoreDAO dao = new VideoStoreDAO();

    public AddMemberController(){}

    public AddMemberController(Member member, ArrayList<Member> members) {
        this.member = member;
        this.members = members;

    }

    public Member getClan() {
        return member;
    }


    public void clickCancel(ActionEvent actionEvent) {
        member = null;
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private boolean valid(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z') && n.charAt(i) != ' ')
                return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean okEmail(String n) {

        String regex = "^(.+)@(.+.+.+.+.+).(.+)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(n);
        if (matcher.matches()) return true;

        return false;
    }

    private boolean okMobile(String n) {
        Pattern pattern = Pattern.compile("\\d{3}/\\d{7}");
        Pattern pattern1 = Pattern.compile("\\d{3}/\\d{3}-\\d{3}");
        Matcher matcher = pattern.matcher(n);
        Matcher matcher1 = pattern1.matcher(n);
        return (matcher.matches()|| matcher1.matches());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        if (member != null){
            nameField.setText(member.getName());
            mobileField.setText(member.getMobile());
            emailField.setText(member.getEmail());
        }


    }

    private boolean validity(TextField field) {
        if (field.getText().trim().isEmpty()) {
            field.getStyleClass().removeAll("valid");
            field.getStyleClass().add("notValid");
            return false;
        } else {
            field.getStyleClass().removeAll("notValid");
            field.getStyleClass().add("valid");
        }
        return true;
    }

    public void add(ActionEvent actionEvent){

        OK = validity(nameField);
        OK &= validity(emailField);
        OK &= validity(mobileField);


        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(valid(n)) {
                    nameField.getStyleClass().removeAll("notValid");
                    nameField.getStyleClass().add("valid");
                    validName = true;
                }
                else {
                    nameField.getStyleClass().removeAll("valid");
                    nameField.getStyleClass().add("notValid");
                    validName = false;
                    OK = false;
                }
            }
        });


        emailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(okEmail(n)) {
                    emailField.getStyleClass().removeAll("notValid");
                    emailField.getStyleClass().add("valid");
                    validEmail = true;
                }
                else {
                    emailField.getStyleClass().removeAll("valid");
                    emailField.getStyleClass().add("notValid");
                    validEmail = false;
                    OK = false;
                }
            }
        });


        mobileField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(okMobile(n)) {
                    mobileField.getStyleClass().removeAll("notValid");
                    mobileField.getStyleClass().add("valid");
                    validMobile = true;

                }
                else {
                    mobileField.getStyleClass().removeAll("valid");
                    mobileField.getStyleClass().add("notValid");
                    validMobile = false;
                    OK = false;
                }
            }
        });

        if (!OK) return;

        dodajIzmijeniVlasnika(member);

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    public void dodajIzmijeniVlasnika(Member member){
        if (member == null) {
        member = new Member(0, nameField.getText(), mobileField.getText(), emailField.getText());
        dao.addMember(member);

    } else {
        member.setName(nameField.getText());
        member.setMobile(mobileField.getText());
        member.setEmail(emailField.getText());
        dao.changeMember(member);
    }}
}
