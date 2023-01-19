package com.aibrains.upms;

import com.aibrains.upms.Functions;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminDashboardController extends Functions {
    public void regDoc(ActionEvent event) throws IOException {
        openWindow(event,"NewDoctor.fxml");
    }

    public void signout(ActionEvent event) throws IOException {
        openScreen(event,"LoginMain.fxml");
    }
}
