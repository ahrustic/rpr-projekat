package video.store.find;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.classes.Movie;

public class FindMovieYearController {

    public TextField find;
    private VideoStoreDAO dao = new VideoStoreDAO();

    public FindMovieYearController() {
    }

    public void findMovie(ActionEvent actionEvent){
        String actorName = find.getText();
    }

    public Movie find(){
        String year = find.getText();
        Movie movie = dao.findActorMovie(year);
        return movie;
    }

    public void clickCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) find.getScene().getWindow();
        stage.close();
    }


}
