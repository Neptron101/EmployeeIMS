package empims;

import javafx.application.Application;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.sql.*;


/**
 * Created by Sarah Fromming on 10/08/2017.
 */
public class EmployeeUIController implements Initializable {

    @FXML
    private TableView<Employee> EmployeeTbl;

    @FXML
    private TableView<Project> ProjectTbl;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

    @FXML
    private TableColumn<Employee, Integer> idCol;

    @FXML
    private TableColumn<Project, Integer> idColP;

    @FXML
    private TableColumn<Project, String> titleCol;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextArea txtDesc;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSearchP;


    @FXML
    private MenuItem close;

    @FXML
    private Label lblID;

    @FXML
    private Label lblIDP;

    private Methods fill;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fill = new Methods();
        fill.initialize(idCol,firstNameCol,lastNameCol,EmployeeTbl,txtSearch);
        fill.initializeP(idColP, titleCol, ProjectTbl, txtSearchP);
    }

    public void getRowData() {
        fill.getRowData(EmployeeTbl,lblID,txtFirstName,txtLastName,txtEmail,txtPhone,txtPosition);
    }

    public void getRowDataP() {
        txtDesc.setWrapText(true);
        fill.getRowDataP(ProjectTbl, lblIDP,txtTitle,txtDesc);
    }

    public void close() {
        fill.close(lblID);
    }
}

