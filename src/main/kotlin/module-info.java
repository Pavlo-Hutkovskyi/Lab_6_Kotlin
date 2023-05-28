module com.javafx.lab_6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.sql;


    opens com.javafx.lab_6 to javafx.fxml;
    exports com.javafx.lab_6;
}