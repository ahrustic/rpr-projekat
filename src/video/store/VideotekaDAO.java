package video.store;

import video.store.classes.Clan;
import video.store.classes.Film;
import video.store.classes.Zaduzenje;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class VideotekaDAO {

    public static VideotekaDAO instance = null;
    public Connection connection;

    private PreparedStatement getFilmoviUpit, dajFilmUpit, dajClanUpit, dajZaduzenjeUpit, getClanoviUpit, getZaduzenjaUpit,
                              obrisiFilmUpit, obrisiClanUpit, obrisiZaduzenjeUpit, dodajClanUpit, dodajFilmUpit, dodajZaduzenjeUpit, odrediIdClanaUpit,
                              odrediIdFilmaUpit, odrediIdZaduzenjaUpit, nadjiClanaUpit, nadjiFilmUpit, nadjiZaduzenjeUpit, promijeniClanUpit,
                              promijeniFilmUpit, promijeniZaduzenjeUpit;

    public  static VideotekaDAO getInstance(){
        if (instance == null) instance = new VideotekaDAO();
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

    public VideotekaDAO(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:videostore.db");
        }catch (SQLException e) {
            //e.printStackTrace();
        }
        try {
            getFilmoviUpit = connection.prepareStatement("SELECT * FROM film");
        }catch (SQLException e) {
            regenerisiBazu();
            try {
                getFilmoviUpit = connection.prepareStatement("SELECT * FROM film");
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
        }

        try{
            dajClanUpit = connection.prepareStatement("SELECT * FROM clan WHERE id=?");
            dajFilmUpit = connection.prepareStatement("SELECT * FROM film WHERE id=?");
            dajZaduzenjeUpit = connection.prepareStatement("SELECT * FROM zaduzenje WHERE id=?");


            getClanoviUpit = connection.prepareStatement("SELECT * FROM clan");
            getFilmoviUpit = connection.prepareStatement("SELECT * FROM film");
            getZaduzenjaUpit = connection.prepareStatement("SELECT * FROM zaduzenje");

            obrisiClanUpit = connection.prepareStatement("DELETE FROM clan WHERE id=?");
            obrisiFilmUpit = connection.prepareStatement("DELETE FROM film WHERE id=?");
            obrisiZaduzenjeUpit = connection.prepareStatement("DELETE FROM zaduzenje WHERE id=?");

            dodajClanUpit = connection.prepareStatement("INSERT INTO clan VALUES(?,?,?,?)");
            dodajFilmUpit = connection.prepareStatement("INSERT INTO film VALUES(?,?,?,?,?,?)");
            dodajZaduzenjeUpit = connection.prepareStatement("INSERT INTO zaduzenje VALUES(?,?,?,?,?)");

            odrediIdClanaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM clan");
            odrediIdFilmaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM film");
            odrediIdZaduzenjaUpit = connection.prepareStatement("SELECT MAX(id)+1 FROM zaduzenje");

            nadjiClanaUpit = connection.prepareStatement("SELECT * FROM clan WHERE naziv=?");
            nadjiFilmUpit = connection.prepareStatement("SELECT * FROM film WHERE naziv=?");
            nadjiZaduzenjeUpit = connection.prepareStatement("SELECT * FROM zaduzenje WHERE id=?");

            promijeniClanUpit = connection.prepareStatement("UPDATE clan SET naziv=?, email=?, broj_telefona=? WHERE id=?");
            promijeniFilmUpit = connection.prepareStatement("UPDATE film SET naziv=?, zanr=?, godina_izdanja=?, glavni_glumac=?, kolicina=? WHERE id=?");
            promijeniZaduzenjeUpit = connection.prepareStatement("UPDATE zaduzenje SET film=?, clan=?, datum_zaduzenja=?, datum_razduzenja=? WHERE id=?");


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

    private Clan dajClan(int id) {
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

    private Clan dajClanIzResultSeta(ResultSet rs) throws SQLException {
        return new Clan(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
    }

    private Film dajFilm(int id) {
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

    private Film dajFilmIzResultSeta(ResultSet rs) throws SQLException {
        return new Film(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
    }

    private Zaduzenje dajZaduzenje(int id) {
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

    private Zaduzenje dajZaduzenjeIzResultSeta(ResultSet rs) throws SQLException {
        return new Zaduzenje(rs.getInt(1), dajClan(rs.getInt(2)), dajFilm(rs.getInt(3)) , rs.getDate(4).toLocalDate(), rs.getDate(5).toLocalDate());
    }

    public ArrayList<Film> filmovi() {
        ArrayList<Film> rezultat = new ArrayList();
        try {
            ResultSet rs = getFilmoviUpit.executeQuery();
            while (rs.next()) {
                Film film = dajFilmIzResultSeta(rs);
                rezultat.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Clan> clanovi() {
        ArrayList<Clan> rezultat = new ArrayList();
        try {
            ResultSet rs = getClanoviUpit.executeQuery();
            while (rs.next()) {
                Clan clan = dajClanIzResultSeta(rs);
                rezultat.add(clan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Zaduzenje> zaduzenja() {
        ArrayList<Zaduzenje> rezultat = new ArrayList();
        try {
            ResultSet rs = getZaduzenjaUpit.executeQuery();
            while (rs.next()) {
                Zaduzenje zaduzenje = dajZaduzenjeIzResultSeta(rs);
                rezultat.add(zaduzenje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void obrisiClan(Clan clan) {
        try {
            obrisiClanUpit.setInt(1, clan.getId());
            obrisiClanUpit.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void obrisiFilm(Film film) {
        try {
            obrisiFilmUpit.setInt(1, film.getId());
            obrisiFilmUpit.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void obrisiZaduzenje(Zaduzenje zaduzenje) {
        try {
            obrisiZaduzenjeUpit.setInt(1, zaduzenje.getId());
            obrisiZaduzenjeUpit.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void dodajClan(Clan clan) {
        try {
            ResultSet rs = odrediIdClanaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajClanUpit.setInt(1, id);
            dodajClanUpit.setString(2, clan.getNaziv());
            dodajClanUpit.setString(3, clan.getEmail());
            dodajClanUpit.setString(4, clan.getBrojTelefona());
            dodajClanUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajFilm(Film film) {
        try {
            ResultSet rs = odrediIdFilmaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajFilmUpit.setInt(1, id);
            dodajFilmUpit.setString(2, film.getNaziv());
            dodajFilmUpit.setString(3, film.getZanr());
            dodajFilmUpit.setInt(4, film.getGodnaIzdanja());
            dodajFilmUpit.setString(5, film.getGlavniGlumac());
            dodajFilmUpit.setInt(6, film.getKolicina());

            dodajFilmUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajZaduzenje(Zaduzenje zaduzenje) {
        try {
            ResultSet rs = odrediIdZaduzenjaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajZaduzenjeUpit.setInt(1, id);
            dodajZaduzenjeUpit.setInt(2, zaduzenje.getClan().getId());
            dodajZaduzenjeUpit.setInt(3, zaduzenje.getFilm().getId());
            dodajZaduzenjeUpit.setDate(4, Date.valueOf(zaduzenje.getDatumZaduzenja()));
            dodajZaduzenjeUpit.setDate(4, Date.valueOf(zaduzenje.getDatumRazduzenja()));
            dodajZaduzenjeUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Clan nadjiClan(String nazivClana) {
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

    public Film nadjiFilm(String nazivFilma) {
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

    public Zaduzenje nadjiZaduzenje(String idZaduzenja) {
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

    public void promijeniClana(Clan clan) {
        try {

            promijeniClanUpit.setString(1, clan.getNaziv());
            promijeniClanUpit.setString(2, clan.getEmail());
            promijeniClanUpit.setString(3, clan.getBrojTelefona());
            promijeniClanUpit.setInt(4, clan.getId());
            promijeniClanUpit.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void promijeniFilm(Film film) {
        try {

            promijeniFilmUpit.setString(1, film.getNaziv());
            promijeniFilmUpit.setString(2, film.getZanr());
            promijeniFilmUpit.setInt(3, film.getGodnaIzdanja());
            promijeniFilmUpit.setString(4, film.getGlavniGlumac());
            promijeniFilmUpit.setInt(5, film.getKolicina());
            promijeniFilmUpit.setInt(6, film.getId());

            promijeniFilmUpit.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void promijeniZaduzenje(Zaduzenje zaduzenje) {
        try {

            promijeniZaduzenjeUpit.setInt(1, zaduzenje.getClan().getId());
            promijeniZaduzenjeUpit.setInt(2, zaduzenje.getFilm().getId());
            promijeniZaduzenjeUpit.setDate(3, Date.valueOf(zaduzenje.getDatumZaduzenja()));
            promijeniZaduzenjeUpit.setDate(4, Date.valueOf(zaduzenje.getDatumRazduzenja()));
            promijeniZaduzenjeUpit.setInt(5, zaduzenje.getId());
            promijeniZaduzenjeUpit.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
