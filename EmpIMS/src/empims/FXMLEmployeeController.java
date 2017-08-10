package empims;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;
import java.sql.*;
import java.util.stream.IntStream;

/**
 * Created by Sarah Fromming on 10/08/2017.
 */
public class FXMLEmployeeController implements Initializable {

    @FXML
    private TableView<Employee> EmployeeTbl;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtSearch;

    private ObservableList<Employee> EmpData;
    private DbConnection dc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dc = new DbConnection();
        try {
            Connection conn = dc.Connect();
            EmpData = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Employee");
            ;
            while (rs.next()) {
                //get string from db,whichever way
                EmpData.add(new Employee(rs.getString("FirstName"), rs.getString("LastName")));
            }

        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        //Set cell value factory to tableview.
        //NB.PropertyValue Factory must be the same with the one set in model class.
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        EmployeeTbl.setItems(null);
        EmployeeTbl.setItems(EmpData);

        FilteredList<Employee> filteredData = new FilteredList<>(EmpData, p -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(EmployeeTbl.comparatorProperty());

        EmployeeTbl.setItems(sortedData);
    }

    public void getRowData() {
        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        Employee employee = EmployeeTbl.getSelectionModel().getSelectedItem();
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
    }

    public void SaveChanges() {

    }
}

