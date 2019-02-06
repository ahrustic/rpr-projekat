package video.store;

import video.store.classes.Issued;
import video.store.classes.Member;
import video.store.classes.Movie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class VideoStoreDAO {

    public static VideoStoreDAO instance = null;
    public Connection connection;

    private PreparedStatement getMoviesQuery, giveMovieQuery, giveMemberQuery, giveIssuedQuery, getMembersQuery, getIssuedQuery,
            deleteMovieQuery, deleteMemberQuery, deleteIssuedQuery, addMemberQuery, addMovieQuery, addIssuedQuery, IdMemberQuery,
            chooseIdMovieQuery, chooseIdIssuedQuery, chooseMemberByNameQuery, chooseMovieByNameQuery, chooseIssuedQuery, changeMemberQuery,
            changeMovieQuery, changeIssuedQuery;

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
            getMembersQuery = connection.prepareStatement("SELECT * FROM member");
        }catch (SQLException e) {
            regenerate();
            try {
                getMembersQuery = connection.prepareStatement("SELECT * FROM member");
            } catch (SQLException e1) {
                //e1.printStackTrace();
            }
        }

        try{
            giveMemberQuery = connection.prepareStatement("SELECT * FROM member WHERE id=?");
            giveMovieQuery = connection.prepareStatement("SELECT * FROM movie WHERE id=?");
            giveIssuedQuery = connection.prepareStatement("SELECT * FROM issued WHERE id=?");


            getMembersQuery = connection.prepareStatement("SELECT * FROM member");
            getMoviesQuery = connection.prepareStatement("SELECT * FROM movie");
            getIssuedQuery = connection.prepareStatement("SELECT * FROM issued");

            deleteMemberQuery = connection.prepareStatement("DELETE FROM member WHERE id=?");
            deleteMovieQuery = connection.prepareStatement("DELETE FROM movie WHERE id=?");
            deleteIssuedQuery = connection.prepareStatement("DELETE FROM issued WHERE id=?");

            addMemberQuery = connection.prepareStatement("INSERT INTO member VALUES(?,?,?,?,?)");
            addMovieQuery = connection.prepareStatement("INSERT INTO movie VALUES(?,?,?,?,?,?,?)");
            addIssuedQuery = connection.prepareStatement("INSERT INTO issued VALUES(?,?,?,?,?)");

            IdMemberQuery = connection.prepareStatement("SELECT MAX(id)+1 FROM member");
            chooseIdMovieQuery = connection.prepareStatement("SELECT MAX(id)+1 FROM movie");
            chooseIdIssuedQuery = connection.prepareStatement("SELECT MAX(id)+1 FROM issued");

            chooseMemberByNameQuery = connection.prepareStatement("SELECT * FROM member WHERE naziv=?");
            chooseMovieByNameQuery = connection.prepareStatement("SELECT * FROM movie WHERE naziv=?");
            chooseIssuedQuery = connection.prepareStatement("SELECT * FROM zaduzenje WHERE id=?");

            changeMemberQuery = connection.prepareStatement("UPDATE member SET name=?, mobile=?, email=? WHERE id=?");
            changeMovieQuery = connection.prepareStatement("UPDATE movie SET name=?, genre=?, year=?, actor=?, production=?, quantity=? WHERE id=?");
            changeIssuedQuery = connection.prepareStatement("UPDATE issued SET movie=?, member=?, date_charge=?, date_discharge=? WHERE id=?");


        } catch (SQLException e) {
            //e.printStackTrace();
        }

    }

    private void regenerate() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream("videostore.db.sql"));
            String sqlQuery = "";
            while (scanner.hasNext()) {
                sqlQuery += scanner.nextLine();
                if ( sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        //e.printStackTrace();
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
           // e.printStackTrace();
        }
    }

    private Member giveMember(int id) {
        try {
            giveMemberQuery.setInt(1, id);
            ResultSet rs = giveMemberQuery.executeQuery();
            if (!rs.next()) return null;
            return giveMemberFromResultSet(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }

    }

    private Member giveMemberFromResultSet(ResultSet rs) throws SQLException {
        return new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
    }

    private Movie giveMovie(int id) {
        try {
            giveMovieQuery.setInt(1, id);
            ResultSet rs = giveMovieQuery.executeQuery();
            if (!rs.next()) return null;
            return giveMovieFromResultSet(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }

    }

    private Movie giveMovieFromResultSet(ResultSet rs) throws SQLException {
        return new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7));
    }

    private Issued giveIssued(int id) {
        try {
            giveIssuedQuery.setInt(1, id);
            ResultSet rs = giveIssuedQuery.executeQuery();
            if (!rs.next()) return null;
            return giveIssuedFromResultSet(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }

    }

    private Issued giveIssuedFromResultSet(ResultSet rs)  throws SQLException{
        LocalDate charged = rs.getDate(4).toLocalDate();
        LocalDate discharged = rs.getDate(5).toLocalDate();
        return new Issued(rs.getInt(1), giveMember(rs.getInt(2)), giveMovie(rs.getInt(3)) , charged, discharged);
    }

    public ArrayList<Movie> movies() {
        ArrayList<Movie> rezultat = new ArrayList();
        try {
            ResultSet rs = getMoviesQuery.executeQuery();
            while (rs.next()) {
                Movie movie = giveMovieFromResultSet(rs);
                rezultat.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Member> members() {
        ArrayList<Member> rezultat = new ArrayList();
        try {
            ResultSet rs = getMembersQuery.executeQuery();
            while (rs.next()) {
                Member member = giveMemberFromResultSet(rs);
                rezultat.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Issued> issued() {
        ArrayList<Issued> result = new ArrayList();
        try {
            ResultSet rs = getIssuedQuery.executeQuery();
            while (rs.next()) {
                Issued issued = giveIssuedFromResultSet(rs);
                result.add(issued);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteMember(Member member) {
        try {
            deleteMemberQuery.setInt(1, member.getId());
            deleteMemberQuery.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void deleteMovie(Movie movie) {
        try {
            deleteMovieQuery.setInt(1, movie.getId());
            deleteMovieQuery.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void deleteIssued(Issued issued) {
        try {
            deleteIssuedQuery.setInt(1, issued.getId());
            deleteIssuedQuery.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void addMember(Member member) {
        try {

            ResultSet rs = IdMemberQuery.executeQuery();
            int id=1;
            if (rs.next()) id = rs.getInt(1);
            member.setId(id);

            addMemberQuery.setInt(1, member.getId());
            addMemberQuery.setString(2, member.getName());
            addMemberQuery.setString(3, member.getMobile());
            addMemberQuery.setString(4, member.getEmail());
            addMemberQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovie(Movie movie) {
        try {
            ResultSet rs = chooseIdMovieQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addMovieQuery.setInt(1, id);
            addMovieQuery.setString(2, movie.getName());
            addMovieQuery.setString(3, movie.getGenre());
            addMovieQuery.setInt(4, movie.getYear());
            addMovieQuery.setString(5, movie.getActor());
            addMovieQuery.setString(6, movie.getQuantity());

            addMovieQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIssued(Issued issued) {
        try {
            ResultSet rs = chooseIdIssuedQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addIssuedQuery.setInt(1, id);
            addIssuedQuery.setInt(2, issued.getMember().getId());
            addIssuedQuery.setInt(3, issued.getMovie().getId());
            addIssuedQuery.setDate(4, Date.valueOf(issued.getDateCharge()));
            addIssuedQuery.setDate(4, Date.valueOf(issued.getDateDischarge()));
            addIssuedQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Member findMember(String memberName) {
        try {
            chooseMemberByNameQuery.setString(1, memberName);
            ResultSet rs = chooseMemberByNameQuery.executeQuery();
            if (!rs.next()) return null;
            return giveMemberFromResultSet(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public Movie findMovie(String movieTitle) {
        try {
            chooseMovieByNameQuery.setString(1, movieTitle);
            ResultSet rs = chooseMovieByNameQuery.executeQuery();
            if (!rs.next()) return null;
            return giveMovieFromResultSet(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public Issued findIssued(String idIssued) {
        try {
            chooseIssuedQuery.setString(1, idIssued);
            ResultSet rs = chooseIssuedQuery.executeQuery();
            if (!rs.next()) return null;
            return giveIssuedFromResultSet(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public void changeMember(Member member) {
        try {

            changeMemberQuery.setString(1, member.getName());
            changeMemberQuery.setString(2, member.getMobile());
            changeMemberQuery.setString(3, member.getEmail());
            changeMemberQuery.setInt(4, member.getId());
            changeMemberQuery.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeMovie(Movie movie) {
        try {

            changeMovieQuery.setString(1, movie.getName());
            changeMovieQuery.setString(2, movie.getGenre());
            changeMovieQuery.setInt(3, movie.getYear());
            changeMovieQuery.setString(4, movie.getActor());
            changeMovieQuery.setString(5, movie.getQuantity());
            changeMovieQuery.setInt(6, movie.getId());

            changeMovieQuery.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeIssued(Issued issued) {
        try {

            changeIssuedQuery.setInt(1, issued.getMember().getId());
            changeIssuedQuery.setInt(2, issued.getMovie().getId());
            changeIssuedQuery.setDate(3, Date.valueOf(issued.getDateCharge()));
            changeIssuedQuery.setDate(4, Date.valueOf(issued.getDateDischarge()));
            changeIssuedQuery.setInt(5, issued.getId());
            changeIssuedQuery.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


//todo napisati upite za pretrazivanje po godini, zanru, glumcu za film
//todo omoguciti dodavanje i izmjenu
//todo provjeriti kako rijesti problem sa datumom
//todo xml dio
//todo dodati makar jedan izvjesta za stanje filmova
//todo napisati nekoliko osnovnih testova