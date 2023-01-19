package com.aibrains.upms;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LabTestReportController implements Initializable {

    public ImageView labreport;

    public void work() throws SQLException {
        DbConnect connectDB = new DbConnect();
        Connection connection = connectDB.getConnect();
        String connectQueryPhoto = "SELECT * FROM patient_log WHERE sl = "+ PatientDetailsController.reportsl;
        Statement statement = connection.createStatement();
        ResultSet queryOutput = statement.executeQuery(connectQueryPhoto);
        if (queryOutput.next()) {
            Blob blob = queryOutput.getBlob("Report");
            InputStream inputStream = blob.getBinaryStream();
            Image image = new Image(inputStream);
            labreport.setImage(image);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            work();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
