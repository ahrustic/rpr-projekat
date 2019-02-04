package video.store;

import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class VideoStoreDAO {

    public static VideoStoreDAO instance = null;
    public Connection connection;

    private PreparedStatement getFilmoviUpit, dajFilmUpit, dajClanUpit, dajZaduzenjeUpit, getClanoviUpit, getZaduzenjaUpit,
                              obrisiFilmUpit, obrisiClanUpit, obrisiZaduzenjeUpit, dodajClanUpit, dodajFilmUpit, dodajZaduzenjeUpit, odrediIdClanaUpit,
                              odrediIdFilmaUpit, odrediIdZaduzenjaUpit, nadjiClanaUpit, nadjiFilmUpit, nadjiZaduzenjeUpit, promijeniClanUpit,
                              promijeniFilmUpit, promijeniZaduzenjeUpit;

    public  static VideoStoreDAO getInstance(){
        if (instance == null) instance = new VideoStoreDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public VideoStoreDAO(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:videostore.db");
        }catch (SQLException e) {
            //e.printStackTrace();
        }
        try {
            getClanoviUpit = connection.prepareStatement("SELECT * FROM member");
        }catch (SQLException e) {
            regenerisiBazu();
            try {
                getClanoviUpit = connection.prepareStatement("SELECT * FROM member");
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
        }

        try{
            dajClanUpit = connection.prepareStatement("SELECT * FROM member WHERE id=?");
            dajFilmUpit = connection.prepareStatement("SELECT * FROM movie WHERE id=?");
            dajZaduzenjeUpit = connection.prepareStatement("SELECT * FROM issued WHERE id=?");


            getClanoviUpit = connection.prepareStatement("SELECT * FROM member");
            getFilmoviUpit = connection.prepareStatement("SELECT * FROM movie");
            getZaduzenjaUpit = connection.prepareStatement("SELECT * FROM issued");

            obrisiClanUpit = connection.prepareStatement("DELETE FROM member WHERE id=?");
            obrisiFilmUpit = connection.prepareStatement("DELETE FROM movie WHERE id=?");
            obrisiZaduzenjeUpit = connection.prepareStatement("DELETE FROM issued WHERE id=?");

            dodajClanUpit = connection.prepareStatement("INSERT INTO member VALUES(?,?,?,?)");
            dodajFilmUpit = connection.prepareStatement("INSERT INTO movie VALUES(?,?,?,?,?,?)");
            dodajZaduzenjeUpit = connection.prepareStatement("INSERT INTO issued VALUES(?,?,?,?,?)");

            odrediIdClanaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM member");
            odrediIdFilmaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM movie");
            odrediIdZaduzenjaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM issued");

            nadjiClanaUpit = connection.prepareStatement("SELECT * FROM member WHERE naziv=?");
            nadjiFilmUpit = connection.prepareStatement("SELECT * FROM movie WHERE naziv=?");
            nadjiZaduzenjeUpit = connection.prepareStatement("SELECT * FROM zaduzenje WHERE id=?");

            promijeniClanUpit = connection.prepareStatement("UPDATE member SET naziv=?, broj_telefona=?, email=? WHERE id=?");
            promijeniFilmUpit = connection.prepareStatement("UPDATE movie SET naziv=?, zanr=?, godina_izdanja=?, glavni_glumac=?, kolicina=? WHERE id=?");
            promijeniZaduzenjeUpit = connection.prepareStatement("UPDATE issued SET movie=?, member=?, datum_zaduzenja=?, datum_razduzenja=? WHERE id=?");


        } catch (SQLException e) {
            //e.printStackTrace();
        }

    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("videostore.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        //e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
           // e.printStackTrace();
        }
    }

    private Member dajClan(int id) {
        try {
            dajClanUpit.setInt(1, id);
            ResultSet rs = dajClanUpit.executeQuery();
            if (!rs.next()) return null;
            return dajClanIzResultSeta(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }

    }

    private Member dajClanIzResultSeta(ResultSet rs) throws SQLException {
        return new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
    }

    private Movie dajFilm(int id) {
        try {
            dajFilmUpit.setInt(1, id);
            ResultSet rs = dajFilmUpit.executeQuery();
            if (!rs.next()) return null;
            return dajFilmIzResultSeta(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }

    }

    private Movie dajFilmIzResultSeta(ResultSet rs) throws SQLException {
        return new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
    }

    private Issued dajZaduzenje(int id) {
        try {
            dajZaduzenjeUpit.setInt(1, id);
            ResultSet rs = dajZaduzenjeUpit.executeQuery();
            if (!rs.next()) return null;
            return dajZaduzenjeIzResultSeta(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }

    }

    private Issued dajZaduzenjeIzResultSeta(ResultSet rs) throws SQLException {
        return new Issued(rs.getInt(1), dajClan(rs.getInt(2)), dajFilm(rs.getInt(3)) , rs.getDate(4).toLocalDate(), rs.getDate(5).toLocalDate());
    }

    public ArrayList<Movie> filmovi() {
        ArrayList<Movie> rezultat = new ArrayList();
        try {
            ResultSet rs = getFilmoviUpit.executeQuery();
            while (rs.next()) {
                Movie movie = dajFilmIzResultSeta(rs);
                rezultat.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Member> clanovi() {
        ArrayList<Member> rezultat = new ArrayList();
        try {
            ResultSet rs = getClanoviUpit.executeQuery();
            while (rs.next()) {
                Member member = dajClanIzResultSeta(rs);
                rezultat.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Issued> zaduzenja() {
        ArrayList<Issued> rezultat = new ArrayList();
        try {
            ResultSet rs = getZaduzenjaUpit.executeQuery();
            while (rs.next()) {
                Issued issued = dajZaduzenjeIzResultSeta(rs);
                rezultat.add(issued);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void obrisiClan(Member member) {
        try {
            obrisiClanUpit.setInt(1, member.getId());
            obrisiClanUpit.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void obrisiFilm(Movie movie) {
        try {
            obrisiFilmUpit.setInt(1, movie.getId());
            obrisiFilmUpit.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void obrisiZaduzenje(Issued issued) {
        try {
            obrisiZaduzenjeUpit.setInt(1, issued.getId());
            obrisiZaduzenjeUpit.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void dodajClan(Member member) {
        try {
            ResultSet rs = odrediIdClanaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajClanUpit.setInt(1, id);
            dodajClanUpit.setString(2, member.getNaziv());
            dodajClanUpit.setString(3, member.getBrojTelefona());
            dodajClanUpit.setString(4, member.getEmail());
            dodajClanUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajFilm(Movie movie) {
        try {
            ResultSet rs = odrediIdFilmaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajFilmUpit.setInt(1, id);
            dodajFilmUpit.setString(2, movie.getNaziv());
            dodajFilmUpit.setString(3, movie.getZanr());
            dodajFilmUpit.setInt(4, movie.getGodinaIzdanja());
            dodajFilmUpit.setString(5, movie.getGlavniGlumac());
            dodajFilmUpit.setInt(6, movie.getKolicina());

            dodajFilmUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajZaduzenje(Issued issued) {
        try {
            ResultSet rs = odrediIdZaduzenjaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajZaduzenjeUpit.setInt(1, id);
            dodajZaduzenjeUpit.setInt(2, issued.getMember().getId());
            dodajZaduzenjeUpit.setInt(3, issued.getMovie().getId());
            dodajZaduzenjeUpit.setDate(4, Date.valueOf(issued.getDatumZaduzenja()));
            dodajZaduzenjeUpit.setDate(4, Date.valueOf(issued.getDatumRazduzenja()));
            dodajZaduzenjeUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Member nadjiClan(String nazivClana) {
        try {
            nadjiClanaUpit.setString(1, nazivClana);
            ResultSet rs = nadjiClanaUpit.executeQuery();
            if (!rs.next()) return null;
            return dajClanIzResultSeta(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public Movie nadjiFilm(String nazivFilma) {
        try {
            nadjiFilmUpit.setString(1, nazivFilma);
            ResultSet rs = nadjiFilmUpit.executeQuery();
            if (!rs.next()) return null;
            return dajFilmIzResultSeta(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public Issued nadjiZaduzenje(String idZaduzenja) {
        try {
            nadjiZaduzenjeUpit.setString(1, idZaduzenja);
            ResultSet rs = nadjiZaduzenjeUpit.executeQuery();
            if (!rs.next()) return null;
            return dajZaduzenjeIzResultSeta(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public void promijeniClana(Member member) {
        try {

            promijeniClanUpit.setString(1, member.getNaziv());
            promijeniClanUpit.setString(2, member.getBrojTelefona());
            promijeniClanUpit.setString(3, member.getEmail());
            promijeniClanUpit.setInt(4, member.getId());
            promijeniClanUpit.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void promijeniFilm(Movie movie) {
        try {

            promijeniFilmUpit.setString(1, movie.getNaziv());
            promijeniFilmUpit.setString(2, movie.getZanr());
            promijeniFilmUpit.setInt(3, movie.getGodinaIzdanja());
            promijeniFilmUpit.setString(4, movie.getGlavniGlumac());
            promijeniFilmUpit.setInt(5, movie.getKolicina());
            promijeniFilmUpit.setInt(6, movie.getId());

            promijeniFilmUpit.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void promijeniZaduzenje(Issued issued) {
        try {

            promijeniZaduzenjeUpit.setInt(1, issued.getMember().getId());
            promijeniZaduzenjeUpit.setInt(2, issued.getMovie().getId());
            promijeniZaduzenjeUpit.setDate(3, Date.valueOf(issued.getDatumZaduzenja()));
            promijeniZaduzenjeUpit.setDate(4, Date.valueOf(issued.getDatumRazduzenja()));
            promijeniZaduzenjeUpit.setInt(5, issued.getId());
            promijeniZaduzenjeUpit.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
