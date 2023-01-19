package com.aibrains.upms;

import com.aibrains.upms.DbConnect;
import com.aibrains.upms.Functions;
import com.aibrains.upms.LoginMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DoctorDashboardController extends Functions implements Initializable {

    public ImageView photo;
    public Label name;
    public Label degree;
    public Label email;
    public Label spec;
    public Label did;
    public static String docname , emver;
    public Label phone;
    public Label gender;

    public void initialize(URL url, ResourceBundle resourceBundle)  {
        try {


            DbConnect connectDB = new DbConnect();
            Connection connection = connectDB.getConnect();

            String connectQueryPatient = "SELECT * FROM doctor WHERE DID = " + LoginMainController.uid;
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQueryPatient);
            while ((queryOutput.next())) {
                name.setText(queryOutput.getString("FirstName") + " " + queryOutput.getString("LastName"));
              //  docname = queryOutput.getString(queryOutput.getString("FirstName") + " " + queryOutput.getString("LastName"));
                degree.setText(queryOutput.getString("Degree"));
                email.setText(queryOutput.getString("Email"));
                spec.setText(queryOutput.getString("Specialist"));
                did.setText(queryOutput.getString("DID"));
                phone.setText(queryOutput.getString("CellNumber"));
                gender.setText(queryOutput.getString("gender"));
                
                // lastname.setText(queryOutput.getString("LastName"));

            }

            docname = name.getText();

          //  System.out.println(docname.contains("demo"));

            String connectQueryPhoto = "SELECT * FROM doctor WHERE DID = " + LoginMainController.uid;
            statement = connection.createStatement();
            queryOutput = statement.executeQuery(connectQueryPhoto);
            if (queryOutput.next()) {
                Blob blob = queryOutput.getBlob("Image");
                InputStream inputStream = blob.getBinaryStream();
                Image image = new Image(inputStream);
                photo.setImage(image);
            }


        } catch (SQLException e) {
            // throw new RuntimeException(e);
        }
    }

    public void logout(ActionEvent event) throws IOException {
        openScreen(event,"LoginMain.fxml");
    }

    public void PatientDetails(ActionEvent event) throws IOException {
        openWindow(event,"pp.fxml");
    }

    public void addnewP(ActionEvent event) throws IOException {
        openWindow(event,"NewPatient.fxml");
    }
}
