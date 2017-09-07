package empims;

import javafx.beans.binding.Bindings;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.naming.Binding;
import java.sql.*;
import java.util.Optional;

/**
 * Created by Sarah Fromming on 16/08/2017.
 */
public class Methods {
    public ObservableList<Employee> EmpData;

    public ObservableList<Project> ProjectData;

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

	public ObservableList<Project> getProjectData(){
        dc = new DbConnection();
        try{
            Connection connection = dc.Connect();
            ProjectData = FXCollections.observableArrayList();

            //ResultSet resultSet = connection.createStatement().executeQuery("SELECT Projects.ID, Projects.Title FROM Projects;");
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Projects");

            while(resultSet.next()){
                ProjectData.add(new Project(resultSet.getInt("ID"), resultSet.getString("Title"),resultSet.getString("Description")));

            }
            resultSet.close();
            connection.close();
        }
        catch (SQLException e){
            System.out.println("Error" + e);
        }
        return ProjectData;

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
                } else if (Employee.getId().toString().contains(lowerCaseFilter)) {
                    return true;
                }
                Label text = new Label();
                text.setWrapText(true);
                text.setText("The employee you searched for could not be found. Please check the information you entered and try again.");
                //"The employee you searched for could not be found. Please check the information you entered and try again."
                table.setPlaceholder(text);
                return false;
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }
	
	public void filterDataP(TextField searchP, TableView tableP){
        FilteredList<Project> projectFilteredList = new FilteredList<>(ProjectData, project -> true);
        searchP.textProperty().addListener((observable, oldValue, newValue) -> {
            projectFilteredList.setPredicate(Project ->{
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Project.getProjectId().equals(lowerCaseFilter)){
                    return true;
                }
                else if (Project.getProjectTitle().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });

        });

        SortedList<Project> sortedData = new SortedList<Project>(projectFilteredList);
        sortedData.comparatorProperty().bind(tableP.comparatorProperty());
        tableP.setItems(sortedData);

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
	
	public void initializeP(TableColumn projectId, TableColumn projectTitle, TableView projectTable, TextField search){

        ObservableList<Project> ProjectData = getProjectData();

        projectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        projectTitle.setCellValueFactory(new PropertyValueFactory<>("projectTitle"));

        projectTable.setItems(null);
        projectTable.setItems(ProjectData);

        filterDataP(search, projectTable);
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

    public  void getRowDataP(TableView table, Label lbl, TextField title, TextArea desc) {
	    Project project = (Project) table.getSelectionModel().getSelectedItem();
	    lbl.setText(String.valueOf(project.getProjectId()));
	    title.setText(project.getProjectTitle());
	    desc.setText(project.getProjectDesc());
    }

    public void close(Label lbl) {
        Stage app = (Stage) lbl.getScene().getWindow();
        app.close();
    }

    public void end(TableView table, TextField pos, HBox posi, ChoiceBox cb, HBox swap, Button cancel, Button save, Button update, TextField first, TextField last, TextField email, TextField phone, Label lbl) {
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
        update.setVisible(false);


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

    public void update(Label label, TextField first, TextField last, TextField email, TextField phone, ChoiceBox pos) throws SQLException {
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
        pstmt.setString(6, label.getText());
        pstmt.executeUpdate();
        rs.close();
        conn.close();
    }

    public void updateP(Label label, TextField title, TextArea desc) throws SQLException {
        String sql = "UPDATE Project SET Title = ?, Description = ? WHERE ID = ?";
        Connection conn = dc.Connect();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1,title.getText());
        pstmt.setString(2, desc.getText());
        pstmt.setString(3, label.getText());
        pstmt.executeUpdate();
        conn.close();
    }
	
	public void assign (int projectId, int employeeId) throws SQLException {
        String sql = "INSERT INTO Assignment (EmployeeID, ProjectId) VALUES (?,?);";
        System.out.println("P = " + projectId + "E = " +employeeId);
        System.out.println("1");
        dc = new DbConnection();
        Connection connection =dc.Connect();
        System.out.println("2");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, projectId);
            System.out.println(sql);
            preparedStatement.executeUpdate();




        System.out.println("Assigned");


    }
}
