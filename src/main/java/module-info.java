module com.aibrains.upms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.aibrains.upms to javafx.fxml;
    exports com.aibrains.upms;

}