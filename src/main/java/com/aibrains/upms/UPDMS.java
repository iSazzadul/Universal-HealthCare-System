package com.aibrains.upms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class UPDMS extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UPDMS.class.getResource("LoginMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Universal Patient Database Management System ");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/health-logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}