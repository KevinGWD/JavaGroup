module com.example.group_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;


    opens com.example.group_project to javafx.fxml;
    exports com.example.group_project;
}