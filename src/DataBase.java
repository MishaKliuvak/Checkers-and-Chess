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


    public void addResult(String user, String res)
    {

        try
        {
            String query = " insert into results (name, result)"
                    + " values (?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeQuery("SET NAMES 'UTF8'");
            preparedStmt.executeQuery("SET CHARACTER SET 'UTF8'");
            preparedStmt.setString (1, user);
            preparedStmt.setString (2, res);
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}