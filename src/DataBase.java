import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    static Connection conn;
    static boolean connect = false;

    private static DataBase dataBase = null;
    private DataBase() {
    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            DataBase.dataBase = new DataBase();
            try {
                DataBase.dataBase = new DataBase();
                String myDriver = "org.gjt.mm.mysql.Driver";
                Class.forName(myDriver);
                conn = DriverManager.getConnection("jdbc:mysql:///chess-and-checkers?useUnicode=true&characterEncoding=utf-8", "root", "");
                connect = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return DataBase.dataBase;
    }

    public List<String> getGames(String player, String table, String column)
    {
        List<String> data = new ArrayList<String>();
        try
        {
            String query = "SELECT " + column + " FROM " + table + " WHERE id_player='" + player + "'" ;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                String firstName = rs.getString(column);
                data.add(firstName);
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return data;
    }

    public String getLink(String table, int id)
    {
        String link = "";
        try
        {
            String query = "SELECT link FROM " + table + " WHERE id='" + id + "'" ;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                String firstName = rs.getString("link");
                link = firstName;
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return link;
    }


    public void deleteFromDB(String table, int id){

        try {
            String query = "delete from " + table +"  where id = '" + id + "'" ;
            PreparedStatement preparedStmt = null;
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addGame(String table, String name, String description, String link, int id)
    {
        try
        {
            String query = " insert into "  + table + " (id_player, name, description, link)"
                    + " values (?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeQuery("SET NAMES 'UTF8'");
            preparedStmt.executeQuery("SET CHARACTER SET 'UTF8'");
            preparedStmt.setInt (1, id);
            preparedStmt.setString (2, name);
            preparedStmt.setString (3, description);
            preparedStmt.setString (4, link);
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public List<String> getGamesData(int player, String table)
    {
        List<String> data = new ArrayList<String>();
        try
        {
            String query = "SELECT  name, description, link FROM " + table + " WHERE id='" + player + "'" ;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                data.add(rs.getString("name"));
                data.add(rs.getString("description"));
                data.add(rs.getString("link"));
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return data;
    }

    public void update(String table, String name, String descr, String link, int id){
        try {
            String query = "update " + table + " set name = ?, description = ?, link = ? where id = ?";
            PreparedStatement preparedStmt = null;
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, descr);
            preparedStmt.setString(3, link);
            preparedStmt.setInt(4, id);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}