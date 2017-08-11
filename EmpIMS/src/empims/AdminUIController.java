package empims;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by Sarah Fromming on 11/08/2017.
 */
public class AdminUIController {

    @FXML
    private TableView<Employee> EmployeeTbl;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

    @FXML
    private TableColumn<Employee, Integer> idCol;

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
    private Label lblID;

    public void getRowData() {
        Employee employee = EmployeeTbl.getSelectionModel().getSelectedItem();
        lblID.setText(String.valueOf(employee.getId()));
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtEmail.setText(employee.getEmail());
        txtPhone.setText(employee.getPhone());
        txtPosition.setText(employee.getRole());
    }
}
