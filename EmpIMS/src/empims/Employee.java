package empims;
/**
 * Created by Sarah Fromming on 6/08/2017.
 */

import javafx.beans.property.*;
import org.jetbrains.annotations.NotNull;

public class Employee {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    public IntegerProperty idProperty() {
        return id;
    }
    @NotNull
    public final Integer getId() {
        return idProperty().get();
    }
    public final void setId(Integer id) {
        idProperty().set(id);
    }

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

    private final StringProperty email = new SimpleStringProperty(this, "email");
    public StringProperty emailProperty() {
        return email;
    }
    public final String getEmail() {
        return emailProperty().get();
    }
    public final void setEmail(String email) {
        emailProperty().set(email);
    }

    private final StringProperty phone = new SimpleStringProperty(this, "phone");
    public StringProperty phoneProperty() {
        return phone;
    }
    public final String getPhone() {
        return phoneProperty().get();
    }
    public final void setPhone(String phone) {
        phoneProperty().set(phone);
    }

    private final StringProperty role = new SimpleStringProperty(this, "role");
    public StringProperty roleProperty() {
        return role;
    }
    public final String getRole() {
        return roleProperty().get();
    }
    public final void setRole(String role) {
        roleProperty().set(role);
    }



    public Employee(){}

    public Employee(Integer id, String firstName, String lastName, String email, String phone, String role) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
        setRole(role);
    }
}
