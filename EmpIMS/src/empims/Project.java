package empims;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import org.jetbrains.annotations.NotNull;

/**
 * Created by Bikin Maharjan on 19/08/2017.
 */
public class Project {
    //
    private final IntegerProperty projectId = new SimpleIntegerProperty(this, "projectId");
    public IntegerProperty projectIdProperty() {
        return projectId;
    }
    //@NotNull
    public final Integer getProjectId() {return projectIdProperty().get();}
    public final void setProjectId(Integer projectId) {
        projectIdProperty().set(projectId);
    }


    private final StringProperty projectTitle = new SimpleStringProperty(this, "projectTitle");
    public StringProperty projectTitleProperty() {
        return projectTitle;
    }
    public final String getProjectTitle() {
        return projectTitleProperty().get();
    }
    public final void setProjectTitle(String projectTitle) {
        projectTitleProperty().set(projectTitle);
    }

    public Project(){

    }

    public Project(Integer projId, String projTitle){
        setProjectId(projId);
        setProjectTitle(projTitle);
    }


}
