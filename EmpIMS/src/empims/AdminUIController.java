package empims;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javax.swing.text.MaskFormatter;
import java.awt.*;
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
    private TableView<Employee> EmployeeTbl;

    @FXML
    private TableColumn<Employee, Integer> idCol;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fill = new Methods();
        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
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
            if (rs2.next()) {
                String query = "INSERT INTO sql12175092.Employee (FirstName, LastName, Email, Phone, Role) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preStmt = conn.prepareStatement(query);
                preStmt.setString(1, txtFirstName.getText());
                preStmt.setString(2, txtLastName.getText());
                preStmt.setString(3, txtEmail.getText());
                preStmt.setString(4, txtPhone.getText());
                if (rs3.next()) {
                    Integer roleID = rs3.getInt("ID");
                    preStmt.setInt(5, roleID);
                }
                preStmt.execute();
                rs2.close();
                rs3.close();
                conn.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New Employee created");
                alert.setHeaderText(null);
                alert.setContentText("The new record for " + txtFirstName.getText() + " " + txtLastName.getText() + " has been successfully created. The employee ID is: " + lblID.getText());
                alert.showAndWait();
            }
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
        Employee employee = EmployeeTbl.getSelectionModel().getSelectedItem();

        fill.update(employee.getId(),txtFirstName,txtLastName,txtEmail,txtPhone,choiceBox);

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

        fill.delete(employee.getId(),lblID,txtFirstName,txtLastName,txtEmail,txtPhone,txtPosition,EmployeeTbl);
        fill.initialize(idCol, firstNameCol, lastNameCol, EmployeeTbl, txtSearch);
    }
}

