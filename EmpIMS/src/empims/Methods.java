package empims;

import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.sql.*;
import java.util.Optional;

/**
 * Created by Sarah Fromming on 16/08/2017.
 */
public class Methods {
    public ObservableList<Employee> EmpData;
    public DbConnection dc;

    public ObservableList<Employee> getEmpData() {
        dc = new DbConnection();
        try {
            Connection conn = dc.Connect();
            EmpData = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT Employee.ID, Employee.FirstName, Employee.LastName, Employee.Email, Employee.Phone, Roles.Role FROM Employee LEFT JOIN Roles ON Employee.Role = Roles.ID");

            while (rs.next()) {
                //get string from db,whichever way
                EmpData.add(new Employee(rs.getInt("ID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Email"), rs.getString("Phone"), rs.getString("Role")));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        return EmpData;
    }

    public void filterData(TextField search, TableView table) {
        FilteredList<Employee> filteredData = new FilteredList<>(EmpData, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Employee.getId().equals(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }

    public void initialize(TableColumn id, TableColumn first, TableColumn last, TableView table, TextField search) {

        ObservableList<Employee> EmpData = getEmpData();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        table.setItems(null);
        table.setItems(EmpData);

        filterData(search, table);
    }

    public void getRowData(TableView table, Label lbl, TextField first, TextField last, TextField email, TextField phone, TextField pos) {
        Employee employee = (Employee) table.getSelectionModel().getSelectedItem();
        lbl.setText(String.valueOf(employee.getId()));
        first.setText(employee.getFirstName());
        last.setText(employee.getLastName());
        email.setText(employee.getEmail());
        phone.setText(employee.getPhone());
        pos.setText(employee.getRole());

    }

    public void close(Label lbl) {
        Stage app = (Stage) lbl.getScene().getWindow();
        app.close();
    }

    public void end(TableView table, TextField pos, HBox posi, ChoiceBox cb, HBox swap, Button cancel, Button save, TextField first, TextField last, TextField email, TextField phone, Label lbl) {
        table.setDisable(false);

        pos.setLayoutX(252);
        pos.setLayoutY(25);
        posi.getChildren().add(pos);

        pos.setVisible(true);
        cb.setLayoutX(252);
        cb.setLayoutY(25);
        swap.getChildren().add(cb);

        cb.setVisible(false);
        cancel.setVisible(false);
        save.setVisible(false);

        lbl.setText("");
        first.setText("");
        last.setText("");
        email.setText("");
        phone.setText("");
        pos.setText("");

        first.setEditable(false);
        last.setEditable(false);
        email.setEditable(false);
        phone.setEditable(false);
    }

    public void swap(TableView table, TextField pos, HBox posi, ChoiceBox cb, HBox swap, Button cancel, Button save, TextField first, TextField last, TextField email, TextField phone) {
        first.setEditable(true);
        last.setEditable(true);
        email.setEditable(true);
        phone.setEditable(true);

        save.setVisible(true);
        cancel.setVisible(true);

        pos.setLayoutX(252);
        pos.setLayoutY(25);
        swap.getChildren().add(pos);
        cb.setLayoutX(252);
        cb.setLayoutY(25);
        posi.getChildren().add(cb);
        cb.setVisible(true);
        pos.setVisible(false);
    }

    public void delete(int id, Label lbl, TextField first, TextField last, TextField email, TextField phone, TextField pos, TableView table) {
        Employee employee = (Employee) table.getSelectionModel().getSelectedItem();
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Confirmation");
        alert2.setHeaderText("Please confirm the deletion of " + employee.getFirstName() + " " + employee.getLastName());
        alert2.setContentText("Are you sure you want to delete this employee?");

        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK) {
            String sql = "DELETE FROM Employee WHERE ID = ?";

            try (Connection conn = dc.Connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Deletion Successful");
            alert.setContentText(employee.getFirstName() + " " + employee.getLastName() + " has been successfully deleted.");
            alert.showAndWait();

            lbl.setText("");
            first.setText("");
            last.setText("");
            email.setText("");
            phone.setText("");
            pos.setText("");
        } else {

        }
    }

    public void update(int id, TextField first, TextField last, TextField email, TextField phone, ChoiceBox pos, TableView table) throws SQLException {
        String sql = "UPDATE Employee SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, Role = ? WHERE ID = ?";
        Connection conn = dc.Connect();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sql12175092.Roles WHERE Role = '" + pos.getSelectionModel().getSelectedItem().toString() + "'");

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, first.getText());
        pstmt.setString(2, last.getText());
        pstmt.setString(3, email.getText());
        pstmt.setString(4, phone.getText());
        if (rs.next()) {
            Integer roleID = rs.getInt("ID");
            pstmt.setInt(5, roleID);
        }
        pstmt.setInt(6,id);
        pstmt.executeUpdate();
        rs.close();
        conn.close();
    }

    public void addNew() {

    }
}
