package empims;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;
import java.sql.*;

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

    private ObservableList<Employee> data;
    private DbConnection dc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dc = new DbConnection();
        try {
            Connection conn = dc.Connect();
            data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Employee");;
            while (rs.next()) {
                //get string from db,whichever way
                data.add(new Employee(rs.getString("FirstName"), rs.getString("LastName")));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        //Set cell value factory to tableview.
        //NB.PropertyValue Factory must be the same with the one set in model class.
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        EmployeeTbl.setItems(null);
        EmployeeTbl.setItems(data);
    }

    public void searchButtonClicked() {
        System.out.println("Search results...");
    }
}
