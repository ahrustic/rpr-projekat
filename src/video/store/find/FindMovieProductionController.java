package video.store.find;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import video.store.VideoStoreDAO;
import video.store.classes.Movie;

public class FindMovieProductionController {

    public TextField find;
    private VideoStoreDAO dao = new VideoStoreDAO();

    public FindMovieProductionController() {
    }

    public void findMovie(ActionEvent actionEvent){
        String actorName = find.getText();
    }

    public Movie find(){
        String production = find.getText();
        Movie movie = dao.findProductionMovie(production);
        return movie;
    }

    public void clickCancel(ActionEvent actionEvent) {
        Platform.exit();
    }


}
