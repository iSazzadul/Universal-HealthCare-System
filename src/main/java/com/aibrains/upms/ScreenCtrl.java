package com.aibrains.upms;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


    public class ScreenCtrl {
        void openScreen(ActionEvent event, String scName) throws IOException {
            Parent loader = FXMLLoader.load(getClass().getResource(scName));
            Scene scene = new Scene(loader);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }
        void openWindow(ActionEvent event, String scName) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(scName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Credit");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("img/icon.png")));
            stage.setScene(new Scene(root1));
            stage.show();
        }

    }

