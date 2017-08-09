/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empims;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Bikin Maharjan
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button btnLogin;

    @FXML
    private void handlelogInAction() throws IOException {
        System.out.println("Log In");

        Stage loginStage = (Stage) btnLogin.getScene().getWindow();

        FXMLLoader employeeLoader = new FXMLLoader(getClass().getResource("EmployeeUI.fxml"));
        Parent root = (Parent) employeeLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        //stage.setOnHidden(e -> loginStage.show());
        stage.show();
        loginStage.hide();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
