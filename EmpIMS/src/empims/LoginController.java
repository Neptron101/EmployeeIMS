package empims;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


/**
 * Created by Bikin Maharjan on 10/08/2017.
 */
public class LoginController implements Initializable{
    @FXML
    private Label invalid_lbl;
    @FXML
    private TextField userId;
    @FXML
    private TextField pswrd;
    @FXML
    private Button logIn;
    @FXML
    private Text forgotPswrd;

    int ID;
    String PW;

    private DbConnection dC;

    Methods fill = new Methods();


    @FXML
    private void handlelogInAction() throws IOException, SQLException{
        System.out.println("LogIn button Clicked");

        System.out.println("User Id entered = " + userId.getText());
        System.out.println("Password entered = " + pswrd.getText());

        ID = Integer.parseInt(userId.getText());
        PW = pswrd.getText();



        if (isValid()){
            System.out.println("Welcome!");
            Stage loginStage = (Stage) logIn.getScene().getWindow();
            if (isAdmin()){

                System.out.println("Admin Logged In!");

                FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("AdminUI.fxml"));


                Parent root =  adminLoader.load();

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Admin Homepage");
                stage.setScene(scene);
                stage.show();
                loginStage.hide();


            }
            else {
                System.out.println("Employee Logged In!");

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



        }

        else {
            System.out.println("Invalid user name");

            invalid_lbl.setText("Invalid Username or Password!");
            userId.clear();
            pswrd.clear();
        }


    }

    @FXML
    private void handleForgotPassword(){
        System.out.println("Forgot Password Button Clicked");
        if (userId.getText().trim().isEmpty()){
            invalid_lbl.setText("Enter the User Id");
        }
        else {
            invalid_lbl.setText("");

            int empId = Integer.parseInt(userId.getText());


            try {
                fill.forgotPassword(empId);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            Notifications forgotPasswordNotification = Notifications.create()
                    .title("Password Sent")
                    .text("The Password has been sent to your email address")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            forgotPasswordNotification.showInformation();
        }




    }



    private boolean isValid() throws SQLException {

        boolean logIn = false;

        dC = new DbConnection();
        Connection conn = dC.Connect();
        System.out.println("Database Connected!");
        conn.setAutoCommit(false);

        String sql = "SELECT * FROM sql12175092.Login WHERE Password = '"+ PW + "' AND EmpID = "+ ID + ";";
        System.out.println(sql);

        Statement stmt = conn.createStatement();

        ResultSet resultSet = stmt.executeQuery(sql);

        while (resultSet.next()){
            logIn = true;
        }





        System.out.println("Requested " + ID + PW);




        System.out.println("Query Executed");



        stmt.close();
        conn.close();

        return logIn;

    }

    public boolean isAdmin() throws SQLException{
        boolean admin = false;

        dC = new DbConnection();
        Connection connection = dC.Connect();
        connection.setAutoCommit(false);

        String sql = "SELECT * FROM sql12175092.Employee WHERE ID = " + ID + " AND (Role = 1 OR Role = 2);";
        System.out.println(sql);
        Statement stmt = connection.createStatement();

        ResultSet resultSet = stmt.executeQuery(sql);

        while (resultSet.next()){
            admin = true;
        }



        return admin;
    }

    @Override
    public void initialize(URL url,    ResourceBundle rb) {

    }
}
