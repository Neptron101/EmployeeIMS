/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empims;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Bikin Maharjan
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label invalid_lbl;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    private ObservableList<Login> LoginData;
    private DbConnection dc;

    @FXML
    private void handlelogInAction() throws IOException, SQLException {


        if (isValidCredentials()) {
            System.out.println("Log In");
            Stage loginStage = (Stage) btnLogin.getScene().getWindow();

            FXMLLoader employeeLoader = new FXMLLoader(getClass().getResource("EmployeeUI.fxml"));
            Parent root = (Parent) employeeLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            //stage.setOnHidden(e -> loginStage.show());
            stage.show();
            loginStage.hide();
        } else {
            txtUser.clear();
            txtPassword.clear();
            invalid_lbl.setText("Sorry, invalid credentials.");
        }
    }

    private boolean isValidCredentials() throws SQLException {
        boolean let_in = false;
        dc = new DbConnection();

        Connection conn = dc.Connect();
        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();

        int ID = Integer.parseInt(txtUser.getText());
        String PW = txtPassword.getText();

        ResultSet rs = stmt.executeQuery("SELECT * FROM sql12175092.Login WHERE EmpID= " + "'" + ID + "'" + " AND Password= " + "'" + PW + "'");

        while (rs.next()) {
            /*Integer id = rs.getInt("EmpID");
            System.out.println("EmpID = " + id);
            System.out.println("User entered = " + ID);
            String pw = rs.getString("Password");
            System.out.println("Password = " + pw);
            System.out.println("User entered = " + PW);*/
            let_in = true;
        }
        rs.close();
        stmt.close();
        conn.close();
        return let_in;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
