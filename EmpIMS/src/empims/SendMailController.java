package empims;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 * Created by Bikin Maharjan on 16/08/2017.
 */
public class SendMailController implements Initializable{

    @FXML
    private void handleSendMailAction(){
        System.out.println("Send Button Clicked");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void sendMail(String to){


        String from = "web@gmail.com";

        String host = "localhost";

        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try{
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("You have been assigned to a new Project");

            message.setText("Hi," +
                    "You have been assigned to a new Project. Please Log In to your system" +
                    "and go through the Project scope and plan. Thank you.");

            Transport.send(message);
            System.out.println("Message sent successfully");



        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
