package empims;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @FXML
    private Button createReportBtn;

    @FXML
    private Button editReport;



    Integer projId;
    private AdminUIController project;

    public void initProID(Integer sProjectId) throws SQLException {
        Methods fill = new Methods();

        this.projId = sProjectId;
        System.out.println("Project Id to create project = " + this.projId);
        projectId.setText("Project " + this.projId);

        if (fill.reportExists(this.projId)){
            reportDate.setDisable(true);
            reportTitle.setDisable(true);
            reportDescription.setDisable(true);
            createReportBtn.setDisable(true);

            ArrayList<String> report = fill.retrieveReport(this.projId);

            System.out.println(report);

            reportDate.setValue(LocalDate.parse(report.get(1)));
            reportTitle.setText(report.get(2));
            reportDescription.setText(report.get(3));




        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        project = new AdminUIController();


        System.out.println(projId);



        projectId.setText("Project Report");





    }

    @FXML
    public void handleCreateReportBtnAction() throws SQLException {

        System.out.println("CreateBtnClicked");

        String title = reportTitle.getText();
        String description = reportDescription.getText();

        LocalDate date = reportDate.getValue();

        Methods fill  = new Methods();

        fill.fillReport(date, projId, title, description);
        System.out.println(date);
        System.out.println(projId);
        System.out.println(title);
        System.out.println(description);

        Notifications reportSavedNotification = Notifications.create()
                .title("Report Saved")
                .text("The changes made to the report has been saved")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER);

        reportSavedNotification.showInformation();

    }

    @FXML
    public void handleEditBtnAction() {
        reportDate.setDisable(false);
        reportTitle.setDisable(false);
        reportDescription.setDisable(false);
        createReportBtn.setDisable(false);

    }

}
