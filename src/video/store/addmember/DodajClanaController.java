package video.store.addmember;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import video.store.classes.Clan;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DodajClanaController implements Initializable {

    public TextField nameField;
    public TextField mobileField;
    public TextField emailField;
    private boolean ispravnostNaziva;
    private boolean ispravnostTelefona;
    private boolean ispravnostEmaila;
    private Clan clan;
    private static Clan clan2;
    private ArrayList<Clan> clanovi;
    private boolean sveOk = true;

    public DodajClanaController(){}

    public DodajClanaController(Clan clan, ArrayList<Clan> clanovi) {
        this.clan = clan;
        this.clan2 = clan;
        this.clanovi = clanovi;

    }

    public static Clan getClan() {
        return clan2;
    }


    public void clickCancel(ActionEvent actionEvent) {
        clan = null;
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private boolean ispravnost(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z') && n.charAt(i) != ' ')
                return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean ispravanEmail(String n) {

        String regex = "^(.+)@(.+.+.+.+.+).(.+)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(n);
        if (matcher.matches()) return true;

        return false;
    }

    private boolean ispravanTelefon(String n) {
        Pattern pattern = Pattern.compile("\\d{3}/\\d{7}");
        Pattern pattern1 = Pattern.compile("\\d{3}/\\d{3}-\\d{3}");
        Matcher matcher = pattern.matcher(n);
        Matcher matcher1 = pattern1.matcher(n);
        return (matcher.matches()|| matcher1.matches());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (clan != null){
            nameField.setText(clan.getNaziv());
            mobileField.setText(clan.getBrojTelefona());
            emailField.setText(clan.getEmail());
        }

        ispravnostNaziva = false;
        nameField.getStyleClass().add("nijeValidan");
        ispravnostEmaila = false;
        emailField.getStyleClass().add("nijeValidan");
        ispravnostTelefona = false;
        mobileField.getStyleClass().add("nijeValidan");

        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    nameField.getStyleClass().removeAll("nijeValidan");
                    nameField.getStyleClass().add("validan");
                    ispravnostNaziva = true;
                }
                else {
                    nameField.getStyleClass().removeAll("validan");
                    nameField.getStyleClass().add("nijeValidan");
                    ispravnostNaziva = false;
                    sveOk = false;
                }
            }
        });


        emailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravanEmail(n)) {
                    emailField.getStyleClass().removeAll("nijeValidan");
                    emailField.getStyleClass().add("validan");
                    ispravnostEmaila = true;
                }
                else {
                    emailField.getStyleClass().removeAll("validan");
                    emailField.getStyleClass().add("nijeValidan");
                    ispravnostEmaila = false;
                    sveOk = false;
                }
            }
        });


        mobileField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravanTelefon(n)) {
                    mobileField.getStyleClass().removeAll("nijeValidan");
                    mobileField.getStyleClass().add("validan");
                    ispravnostTelefona = true;
                }
                else {
                    mobileField.getStyleClass().removeAll("validan");
                    mobileField.getStyleClass().add("nijeValidan");
                    ispravnostTelefona = false;
                    sveOk = false;
                }
            }
        });


    }

    public void uredu(javafx.event.ActionEvent actionEvent){
        if (!sveOk) return;

        if (clan == null) clan = new Clan();
        clan.setNaziv(nameField.getText());
        clan.setBrojTelefona((mobileField.getText()));
        clan.setEmail(emailField.getText());
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
