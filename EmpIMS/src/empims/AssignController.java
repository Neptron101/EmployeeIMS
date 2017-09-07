package empims;


import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class AssignController implements Initializable{
    @FXML
    private TextField searchTF;

    @FXML
    private Button searchBtn;

    @FXML
    private Button addToProject;

    @FXML
    private TableView empTable;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

    @FXML
    private TableColumn<Employee, Integer> idCol;

    @FXML
    private TableColumn<Assigned, CheckBox> selectCol;



    @FXML
    private TextField txtSearch;

    private Methods fill;
    private AdminUIController project;
    private Integer projectId;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fill = new Methods();
        fill.initialize(idCol,firstNameCol, lastNameCol, empTable, searchTF);
        empTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        project = new AdminUIController();







    }

    @FXML
    private void handleAddAction() throws SQLException, IOException{

        System.out.println("Add Button Clicked");


        List<ObservableList> employeeList = empTable.getSelectionModel().getSelectedItems();
        System.out.println("List"+employeeList);
        Integer i = employeeList.size();


        System.out.println("initial i = " + i);
        for (int i1 = 0; i1<i ; i1++){
            Employee emp = (Employee) employeeList.get(i1);
            int empId = emp.getId();
            System.out.println("ID " + empId);


            Methods assignment = new Methods();
            System.out.println("P= " + projectId + " E= " + empId);

            assignment.assign(projectId,empId);



        }








        Notifications employeeAddedNotification = Notifications.create()
                .title("Employee/s added")
                .text("Selected employees were added to the project")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER);

        employeeAddedNotification.showInformation();


        WebView webView = new WebView();
        NotificationPane sendMailNotification = new NotificationPane(webView);

        Tab tab1 = new Tab(("Tab 1"));
        tab1.setContent(sendMailNotification);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(tab1);

        sendMailNotification.setText("Send Mail?");









    }

    public void initProID(Integer projectId){
        this.projectId = projectId;
        System.out.println("Project ID received from asd" + projectId);
    }






}
