package empims;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ResourceBundle;

public class NotifyController implements Initializable{

    @FXML
    private Text userList;

    @FXML
    private TextField subjectNotify;

    @FXML
    private TextArea descriptionNotify;

    @FXML
    private Button sendNotification;

    public String emailAddress;


    public void initEmpEmailId(String sEmail){
        userList.setText(sEmail);
        this.emailAddress = sEmail;

    }





    public void handleNotificationBtn(javafx.event.ActionEvent actionEvent) {


        System.out.println("Notify Button Pressed");
        String subject = subjectNotify.getText().toString();
        String description = descriptionNotify.getText().toString();
        String to = emailAddress;


        SendMail sendNotification = new SendMail();
        sendNotification.sendNotification(to, subject, description);






    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userList.setText("Apple");
    }
}