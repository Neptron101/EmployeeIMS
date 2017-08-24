package empims;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

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
}
