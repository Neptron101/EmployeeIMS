package empims;

import javafx.fxml.FXML;

import javax.swing.text.html.ListView;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by Sarah Fromming on 14/09/2017.
 */
public class AssignedUIController {

    @FXML
    private TextField txtSearch;

    @FXML
    private ListView listView;

    private Methods fill;
    private DbConnection db;

    public void initialize(URL url, ResourceBundle rb) throws SQLException {

        Connection connection = db.Connect();


        //String sql = "SELECT * FROM Assignment WHERE ProjectId = " + id ;
        /*String sql = "select assignment.*,employee.* from assignment, employee\n" +
                "where assignment.EmployeeID = employee.ID\n" +
                "and assignment.ProjectID = " + id;

        Statement stmt = connection.createStatement();

        ResultSet resultSet = stmt.executeQuery(sql);


        while (resultSet.next()) {
            int empId = resultSet.getInt("EmployeeID");
            String fName = resultSet.getString("FirstName");
            String lName = resultSet.getString("LastName");
            System.out.println(empId);

                            /*
            employee.setId(empId + 1 );
             ObservableList <Employee> EmployeeData = getEmpData();
            String fName = String.valueOf(EmployeeData.get(empId).getFirstName());
             String lName = String.valueOf(EmployeeData.get(empId).getLastName());


            String list = empId + ") " + fName + " " + lName;
            //listView.getItems().add(list);

        }


        if (listView.getItems() == null) {
            label.setText("No one is assigned to this Project yet.");

        }*/
    }
}
