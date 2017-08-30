package empims;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Bikin Maharjan on 16/08/2017.
 */
public class Assigned {

    private IntegerProperty empID;
    private BooleanProperty selectStatus;

    public Assigned(Integer id, boolean selected){
        empID = new SimpleIntegerProperty(id);
        selectStatus = new SimpleBooleanProperty(selected);
    }

    public IntegerProperty idProperty() {return empID;}

    public  BooleanProperty getSelectStatus() {return selectStatus;}

}
