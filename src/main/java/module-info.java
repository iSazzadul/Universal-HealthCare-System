module com.aibrains.upms {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.aibrains.upms to javafx.fxml;
    exports com.aibrains.upms;
}