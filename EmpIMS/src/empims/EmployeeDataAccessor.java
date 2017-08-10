package empims; /**
 * Created by Sarah Fromming on 6/08/2017.
 */

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

public class EmployeeDataAccessor {
    private Connection con;
    private ObservableList<ObservableList> EmpData;

    public EmployeeDataAccessor(String dbURL, String user, String password) throws SQLException {
        con = DriverManager.getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public List<Employee> getEmployeeList() throws SQLException {
        try (
            Statement stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("select * from sql12175092.Employee");
        ) {
            List<Employee> employeeList = new ArrayList<>();
            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Employee employee = new Employee(firstName, lastName);
                employeeList.add(employee);
            }
            return employeeList;
        }
    }
}
