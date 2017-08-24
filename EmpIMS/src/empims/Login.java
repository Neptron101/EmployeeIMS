package empims;
/**
 * Created by Sarah Fromming on 10/08/2017.
 */

import javafx.beans.property.*;
//import org.jetbrains.annotations.NotNull;

public class Login {
    private final IntegerProperty EmpID = new SimpleIntegerProperty(this, "EmpID");
    public IntegerProperty EmpIDProperty() {
        return EmpID;
    }
    //@NotNull
    public final Integer getEmpID() { return EmpIDProperty().get(); }
    public final void setEmpID(Integer EmpID) {
        EmpIDProperty().set(EmpID);
    }

    private final StringProperty password = new SimpleStringProperty(this, "Password");
    public StringProperty passwordProperty() {
        return password;
    }
    public final String getPassword() {
        return passwordProperty().get();
    }
    public final void setPassword(String password) { passwordProperty().set(password); }

    public Login(){}

    public Login(Integer EmpID, String password) {
        setEmpID(EmpID);
        setPassword(password);
    }
}
