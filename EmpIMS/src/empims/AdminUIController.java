package empims;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminUIController implements Initializable {

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem add;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSearchP;

    @FXML
    private TableView<Employee> EmployeeTbl;

    @FXML
    private TableColumn<Employee, Integer> idCol;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

    @FXML
    private TableView<Project> ProjectTbl;

    @FXML
    private  TableColumn<Project, Integer> projectIdCol;

    @FXML
    private TableColumn<Project, String> projectTitleCol;


    @FXML
    private Label lblReady;

    @FXML
    private Label lblID;

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
    private ChoiceBox choiceBox;

    @FXML
    private HBox hBoxPos;

    @FXML
    private HBox hBoxSwap;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private MenuItem delete;

    @FXML
    private MenuItem modify;

    @FXML
    private ListView employeeListView;

    @FXML
    private Label projectStatus;

    @FXML
    private Button assignBtn;

    @FXML
    private Button writeReportBtn;




    private Methods fill;
    private DbConnection db;
    private ObservableList roles;


    //TO pass to assign Stage
    private Integer projectId;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        fill = new Methods();
        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
        fill.initializeP(projectIdCol,projectTitleCol, ProjectTbl, txtSearchP);
        ProjectTbl.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

    }

    public Integer projectId(){
        Project project= ProjectTbl.getSelectionModel().getSelectedItem();
        Integer projectId = project.getProjectId();
        return projectId;

    }

    public void getProjectRowData() throws SQLException {
        System.out.println("call");
        fill.getProjectRowData(ProjectTbl, projectStatus, employeeListView);
        if (fill.reportExists(ProjectTbl.getSelectionModel().getSelectedItem().getProjectId())){
            writeReportBtn.setText("View Report");

        }
        else {
            writeReportBtn.setDisable(false);
            writeReportBtn.setText("Write Report");
        }


    }

    public void getRowData() {
        fill.getRowData(EmployeeTbl, lblID, txtFirstName, txtLastName, txtEmail, txtPhone, txtPosition);
        delete.setDisable(false);
        modify.setDisable(false);
    }

    public void close() {
        fill.close(lblID);
    }


    public void addNew() {
        fill.swap(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, txtFirstName, txtLastName, txtEmail, txtPhone);
        EmployeeTbl.setDisable(true);
        Integer last = EmployeeTbl.getItems().size();
        Employee employee = EmployeeTbl.getItems().get(last - 1);
        lblID.setText(String.valueOf(employee.getId() + 1));
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");

        db = new DbConnection();
        try {
            Connection conn = db.Connect();
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Roles");
            roles = FXCollections.observableArrayList();
            while (rs.next()) {
                //get string from db,whichever way
                roles.add(rs.getString("Role"));
            }
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        choiceBox.setItems(FXCollections.observableArrayList(roles));
    }

    public void save() {
        try {
            Connection conn = db.Connect();
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Employee");
            ResultSet rs3 = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Roles WHERE Role = '" + choiceBox.getSelectionModel().getSelectedItem().toString() + "'");
            roles = FXCollections.observableArrayList();
            if (rs2.next()) {
                String query = "INSERT INTO sql12175092.Employee (FirstName, LastName, Email, Phone, Role) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preStmt = conn.prepareStatement(query);
                preStmt.setString(1, txtFirstName.getText());
                preStmt.setString(2, txtLastName.getText());
                preStmt.setString(3, txtEmail.getText());
                preStmt.setString(4, txtPhone.getText());
                String role = rs2.getString("Role");
                if (rs3.next()) {
                    Integer roleID = rs3.getInt("ID");
                    preStmt.setInt(5, roleID);
                }
                preStmt.execute();
                rs2.close();
                rs3.close();
                conn.close();
            }
        } catch (
                SQLException ex) {
            System.err.println("Error" + ex);
        }
        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
        fill.end(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, txtFirstName, txtLastName, txtEmail, txtPhone, lblID);
    }

    public void cancel() {
        fill.end(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, txtFirstName, txtLastName, txtEmail, txtPhone, lblID);
        delete.setDisable(true);
    }


    public void modify() {
        fill.swap(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, txtFirstName, txtLastName, txtEmail, txtPhone);
    }

    public void delete() {
        Employee employee = EmployeeTbl.getSelectionModel().getSelectedItem();

        System.out.println(employee.getId());
        fill.delete(employee.getId());
        System.out.println("Successful deleted");

        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
    }

    @FXML
    public void handleAssignBtnAction() throws IOException{
        System.out.println("Assign Button Clicked");


        Project project= ProjectTbl.getSelectionModel().getSelectedItem();
        projectId = project.getProjectId();

        System.out.println("Project ID sent = " + projectId);

        Project p = new Project();
        p.setProId(projectId);

        FXMLLoader assignLoader = new FXMLLoader(getClass().getResource("Assign.fxml"));


        Parent root =  assignLoader.load();

        //Pass project Id to another window
        AssignController controller = assignLoader.<AssignController>getController();
        controller.initProID(projectId);

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Assign Employee to the Project");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void handleWriteReportBtnAction() throws SQLException {
        System.out.println("Report btn Clicked");
        Project project= ProjectTbl.getSelectionModel().getSelectedItem();
        projectId = project.getProjectId();



        System.out.println("Project Id sent to write report = " + projectId);

        FXMLLoader reportLoader = new FXMLLoader(getClass().getResource("ReportUI.fxml"));


        Parent root = null;
        try {
            root = reportLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Pass project Id to another window
        ReportUIController controller = reportLoader.<ReportUIController>getController();
        controller.initProID(projectId);

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Write Report");
        stage.setScene(scene);
        stage.show();


    }


}

