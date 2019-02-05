package video.store.add.movie;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import video.store.classes.Movie;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {

    public TextField titleField;
    public TextField genreField;
    public TextField yearField;
    public TextField actorField;
    public TextField productionField;
    public TextField quatityField;
    private boolean validTitle;
    private boolean validGenre;
    private boolean validYear;
    private boolean validActor;
    private boolean validProduction;
    private boolean validQuantity;
    public Movie movie;
    private boolean OK = true;

    public AddMovieController(){}

    public AddMovieController(Movie movie, ArrayList<Movie> filmovi) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public void clickCancel(ActionEvent actionEvent) {
        movie = null;
        Stage stage = (Stage) titleField.getScene().getWindow();
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

    private boolean ispravnostKolicine(String n){
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= '0' && n.charAt(i) <= '9'))
                return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean ispravnostGodine(String n){
        if (n.length() != 4) return false;
        String trenutnaGodina = "";
        LocalDate godina = LocalDate.now();
        trenutnaGodina += godina.getYear();
        return !trenutnaGodina.equals(n);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        OK = validity(titleField);
        OK &= validity(genreField);
        OK &= validity(yearField);
        OK = validity(actorField);
        OK &= validity(productionField);
        OK &= validity(quatityField);

        if (movie != null) {
            titleField.setText(movie.getName());
            genreField.setText(movie.getGenre());
            yearField.setText(Integer.toString(movie.getYear()));
            actorField.setText(movie.getActor());
            productionField.setText(movie.getProduction());
            quatityField.setText(Integer.toString(movie.getQuantity()));
        }

        validTitle = false;
        validGenre = false;
        validActor = false;
        validYear = false;
        validProduction = false;
        validQuantity = false;
        titleField.getStyleClass().add("notValid");
        genreField.getStyleClass().add("notValid");
        yearField.getStyleClass().add("notValid");
        actorField.getStyleClass().add("notValid");
        productionField.getStyleClass().add("notValid");
        quatityField.getStyleClass().add("notValid");

        titleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    titleField.getStyleClass().removeAll("notValid");
                    titleField.getStyleClass().add("validan");
                    validTitle = true;
                }
                else {
                    titleField.getStyleClass().removeAll("validan");
                    titleField.getStyleClass().add("notValid");
                    validTitle = false;
                    OK = false;
                }
            }
        });

        genreField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    genreField.getStyleClass().removeAll("notValid");
                    genreField.getStyleClass().add("validan");
                    validGenre = true;
                    OK = true;
                }
                else {
                    genreField.getStyleClass().removeAll("validan");
                    genreField.getStyleClass().add("notValid");
                    validGenre = false;
                    OK = false;
                }
            }
        });

        yearField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnostGodine(n)) {
                    yearField.getStyleClass().removeAll("notValid");
                    yearField.getStyleClass().add("validan");
                    validYear = true;
                    OK = true;
                }
                else {
                    yearField.getStyleClass().removeAll("validan");
                    yearField.getStyleClass().add("notValid");
                    validYear = false;
                    OK = false;
                }
            }
        });

        actorField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    actorField.getStyleClass().removeAll("notValid");
                    actorField.getStyleClass().add("validan");
                    validActor = true;
                    OK = true;
                }
                else {
                    actorField.getStyleClass().removeAll("validan");
                    actorField.getStyleClass().add("notValid");
                    validActor = false;
                    OK = false;
                }
            }
        });

        quatityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnostKolicine(n)) {
                    quatityField.getStyleClass().removeAll("notValid");
                    quatityField.getStyleClass().add("validan");
                    validQuantity = true;
                    OK = true;
                }
                else {
                    quatityField.getStyleClass().removeAll("validan");
                    quatityField.getStyleClass().add("notValid");
                    validQuantity = false;
                    OK = false;
                }
            }
        });

        productionField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if(ispravnost(n)) {
                    productionField.getStyleClass().removeAll("notValid");
                    productionField.getStyleClass().add("validan");
                    validProduction = true;
                }
                else {
                    productionField.getStyleClass().removeAll("validan");
                    productionField.getStyleClass().add("notValid");
                    validProduction = false;
                    OK = false;
                }
            }
        });

    }

    private boolean validity(TextField polje) {
        if (polje.getText().trim().isEmpty()) {
            polje.getStyleClass().removeAll("valid");
            polje.getStyleClass().add("notValid");
            return false;
        } else {
            polje.getStyleClass().removeAll("notValid");
            polje.getStyleClass().add("valid");
        }
        return true;
    }

    public void ok(javafx.event.ActionEvent actionEvent){
        if (!OK) return;

        if (movie == null) movie = new Movie();
        movie.setName(titleField.getText());
        movie.setGenre((genreField.getText()));
        movie.setYear(Integer.parseInt(yearField.getText()));
        movie.setActor(actorField.getText());
        movie.setQuantity(Integer.parseInt(quatityField.getText()));
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

}
