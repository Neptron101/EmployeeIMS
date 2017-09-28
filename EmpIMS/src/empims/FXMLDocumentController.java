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
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.ImageView;

/**
 * @author Bikin Maharjan
 */
public class FXMLDocumentController implements Initializable {
    public boolean admin = false;

    @FXML
    private Label invalid_lbl;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    private DbConnection dc;

    @FXML
    private void handlelogInAction() throws IOException, SQLException {
        if (isValidCredentials()) {
            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            if (admin == true) {
                System.out.println("Admin Log In...");

                FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("AdminUI.fxml"));
                Parent root = adminLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.getIcons().add(new Image("/empims/images/Logo.png"));
                stage.resizableProperty().setValue(false);
                stage.show();
                loginStage.hide();
            } else {
                System.out.println("Employee Log In...");

                FXMLLoader employeeLoader = new FXMLLoader(getClass().getResource("EmployeeUI.fxml"));
                Parent root = employeeLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image("/empims/images/Logo.png"));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.resizableProperty().setValue(false);
                stage.show();
                loginStage.hide();
            }
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
        //conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();
        Statement stmt2 = conn.createStatement();

        int ID = Integer.parseInt(txtUser.getText());
        String PW = txtPassword.getText();

        ResultSet rs = stmt.executeQuery("SELECT * FROM sql12175092.Login WHERE EmpID= " + "'" + ID + "'" + " AND Password= " + "'" + PW + "'");

        while (rs.next()) {
            ResultSet rs2 = stmt2.executeQuery("SELECT * from sql12175092.Employee where ID = " + "'" + ID + "'");
            rs2.next();

            //while (rs2.next()) {
            System.out.println(rs2.getInt("Role"));
                if ((rs2.getInt("Role") == 1) || (rs2.getInt("Role") == 2)) {
                    admin = true;
                }
            //}
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
