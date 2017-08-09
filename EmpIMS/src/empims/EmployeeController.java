package empims;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;


/**
 * Created by Sarah Fromming on 9/08/2017.
 */
public class EmployeeController {
    private EmployeeDataAccessor dataAccessor;

    @FXML
    private TableColumn<Employee, String> firstNameCol;

    @FXML
    private TableColumn<Employee, String> lastNameCol;

    @FXML
    private TableView<Employee> EmployeeTbl;

    public void searchButtonClicked() {
        System.out.println("Search results...");
    }

    @FXML
    void initialize() throws SQLException {
        dataAccessor = new EmployeeDataAccessor("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12175092?zeroDateTimeBehavior=convertToNull","sql12175092","IKiL6BaUyu");
        System.out.println("Database Connected..");
        System.out.println("");
    }

    /*@Override
    public void start(Stage primaryStage) throws Exception {
        dataAccessor = new EmployeeDataAccessor("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12175092?zeroDateTimeBehavior=convertToNull","sql12175092","IKiL6BaUyu");
        System.out.println("Database Connected..");
        System.out.println("");

        //firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        //lastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));

        //EmployeeTbl.getColumns().addAll(firstNameCol, lastNameCol);
        //EmployeeTbl.getItems().addAll(dataAccessor.getEmployeeList());

    }

    public static void main(String[] args) {
        launch(args);
    }*/
}
