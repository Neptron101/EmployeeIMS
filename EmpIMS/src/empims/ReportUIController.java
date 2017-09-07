package empims;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;



public class ReportUIController implements Initializable{

    @FXML
    private TextField reportTitle;

    @FXML
    private TextArea reportDescription;

    @FXML
    private Label projectId;

    @FXML
    private DatePicker reportDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectId.setText("Project 1");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ssd = LocalDate.parse("2015-01-12");

        reportDate.setValue(ssd);

    }

    @FXML
    public void handleCreateReportBtnAction(){

        System.out.println("CreateBtnClicked");
        int project = 1;
        String title = reportTitle.getText();
        String description = reportDescription.getText();

        LocalDate asdf = reportDate.getValue();

        System.out.println(asdf);
        System.out.println(project);
        System.out.println(title);
        System.out.println(description);

    }
}
