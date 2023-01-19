package com.aibrains.upms;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Functions {

    int checkIdEmail(String id, String Email) throws SQLException {
        DbConnect connectDB = new DbConnect();
        Connection connection = connectDB.getConnect();
        String connectQueryLog = "SELECT * FROM `user` ";
        Statement statement = connection.createStatement();
        ResultSet queryOutput = statement.executeQuery(connectQueryLog);


            while (queryOutput.next()) {
                String ID = queryOutput.getString("PID");
                String email = queryOutput.getString("email");

                if (ID.equals(id) || email.equals(Email)) {
                    return 0;
                }

            }
        return 1;

    }


        void openScreen(ActionEvent event, String scName) throws IOException {
            Parent loader = FXMLLoader.load(getClass().getResource(scName));
            Scene scene = new Scene(loader);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }
        protected void openWindow(ActionEvent event, String scName) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(scName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Universal Patient Database Management System");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("img/health-logo.png")));
            stage.setScene(new Scene(root1));
            stage.show();
        }

    }




