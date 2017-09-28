package empims;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.*;

/**
 * Created by Sarah Fromming on 10/08/2017.
 */
public class DbConnection {
    public Connection Connect() {
        try {
            //Your database url string,ensure it is correct
            /*String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12175092?zeroDateTimeBehavior=convertToNull";
            String user = "sql12175092";
            String password = "IKiL6BaUyu";
            */
            String url = "jdbc:mysql://localhost:3306/sql12175092?verifyServerCertificate=false&useSSL=true";
            //String url = "jdbc:mysql://192.168.43.215:3306/sql12175092?verifyServerCertificate=false&useSSL=true";
            String user = "root";
            String password = "icq123SS";


            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
