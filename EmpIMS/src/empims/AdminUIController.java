package empims;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
    private TableColumn<Project, Integer> projectIdCol;

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
    private Button btnUpdate;

    @FXML
    private MenuItem delete;

    @FXML
    private MenuItem modify;

    private Methods fill;
    private DbConnection db;
    private ObservableList roles;

    //TO pass to assign Stage
    public Integer projectId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fill = new Methods();
        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
        fill.initializeP(projectIdCol, projectTitleCol, ProjectTbl, txtSearchP);
        ProjectTbl.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
    }

    public Integer projectId() {
        Project project = ProjectTbl.getSelectionModel().getSelectedItem();
        Integer projectId = project.getProjectId();
        return projectId;

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
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");

        db = new DbConnection();
        try {
            Connection conn = db.Connect();
            String sql = "INSERT INTO sql12175092.Employee VALUE ()";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Employee ORDER BY ID DESC LIMIT 1");
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Roles");
            roles = FXCollections.observableArrayList();
            if (rs2.next()) {
                System.out.println("Successfully interserted");
                lblID.setText(rs2.getString("ID"));
            }
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
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Roles WHERE Role = '" + choiceBox.getSelectionModel().getSelectedItem().toString() + "'");
            if (rs.next()) {
                fill.update(lblID, txtFirstName, txtLastName, txtEmail, txtPhone, choiceBox);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Employee created");
            alert.setHeaderText(null);
            alert.setContentText("The new record for " + txtFirstName.getText() + " " + txtLastName.getText() + " has been successfully created. The employee ID is: " + lblID.getText());
            alert.showAndWait();

        } catch (
                SQLException ex) {
            System.err.println("Error" + ex);
        }
        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
        fill.end(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, btnUpdate, txtFirstName, txtLastName, txtEmail, txtPhone, lblID);
    }

    public void cancel() {
        fill.end(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, btnUpdate, txtFirstName, txtLastName, txtEmail, txtPhone, lblID);
        delete.setDisable(true);
    }

    public void modify() {
        String position = txtPosition.getText();
        fill.swap(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, txtFirstName, txtLastName, txtEmail, txtPhone);
        btnUpdate.setVisible(true);
        btnSave.setVisible(false);
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
        choiceBox.getSelectionModel().select(position);
    }

    public void update() throws SQLException {
        fill.update(lblID, txtFirstName, txtLastName, txtEmail, txtPhone, choiceBox);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Employee information successfully updated.");
        alert.showAndWait();

        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
        fill.end(EmployeeTbl, txtPosition, hBoxPos, choiceBox, hBoxSwap, btnCancel, btnSave, btnUpdate, txtFirstName, txtLastName, txtEmail, txtPhone, lblID);
    }

    public void delete() {
        Employee employee = EmployeeTbl.getSelectionModel().getSelectedItem();

        fill.delete(employee.getId(), lblID, txtFirstName, txtLastName, txtEmail, txtPhone, txtPosition, EmployeeTbl);
        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
    }

    @FXML
    public void handleAssignBtnAction() throws IOException {
        System.out.println("Assign Button Clicked");

        Project project = ProjectTbl.getSelectionModel().getSelectedItem();
        projectId = project.getProjectId();

        System.out.println("Project ID sent = " + projectId);

        FXMLLoader assignLoader = new FXMLLoader(getClass().getResource("Assign.fxml"));

        Parent root = assignLoader.load();

        AssignController controller = assignLoader.<AssignController>getController();
        controller.initProID(projectId);


        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Assign Employee to the Project");
        stage.setScene(scene);
        stage.show();


    }

    public Integer getProjectId() {
        System.out.println("TEST" + projectId);
        return projectId;
    }

}

