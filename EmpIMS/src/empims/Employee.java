package empims; /**
 * Created by Sarah Fromming on 6/08/2017.
 */

import javafx.beans.property.*;

public class Employee {
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public final String getFirstName() {
        return firstNameProperty().get();
    }
    public final void setFirstName(String firstName) {
        firstNameProperty().set(firstName);
    }

    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public final String getLastName() {
        return lastNameProperty().get();
    }
    public final void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    public Employee(){}

    public Employee(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
}
