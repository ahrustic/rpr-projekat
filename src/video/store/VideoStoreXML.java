package video.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class VideoStoreXML {
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Issued> issued = new ArrayList<>();

    VideoStoreXML() {
        readFiles();
    }

    private void readFiles() {
        members.clear();
        movies.clear();
        issued.clear();
        try {
            XMLDecoder decoder = new XMLDecoder(new FileInputStream("members.xml"));
            members = (ArrayList<Member>)decoder.readObject();
            decoder.close();
            decoder = new XMLDecoder(new FileInputStream("movies.xml"));
            movies = (ArrayList<Movie>)decoder.readObject();
            decoder.close();
            decoder = new XMLDecoder(new FileInputStream("issued.xml"));
            issued = (ArrayList<Issued>)decoder.readObject();
            decoder.close();

            // Sortiramo liste
            movies.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
            members.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void writeFiles() {
        try {
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream("members.xml"));
            encoder.writeObject(members);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("movies.xml"));
            encoder.writeObject(movies);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("issued.xml"));
            encoder.writeObject(issued);
            encoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Member> getVlasnici() {
        return FXCollections.observableArrayList(members);
    }

    public ObservableList<Movie> getVozila() {
        return FXCollections.observableArrayList(movies);
    }

    public ObservableList<Issued> getMjesta() {
        return FXCollections.observableArrayList(issued);
    }



}
