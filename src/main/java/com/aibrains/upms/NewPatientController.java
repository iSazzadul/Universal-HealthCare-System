package com.aibrains.upms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NewPatientController extends Functions implements Initializable {
    public TextField firstname;
    public TextField lastname;
    public TextField age;
    public TextField address;
    public DatePicker date;
    public TextField PID;
    public Button save;
    public RadioButton Operation;
    public RadioButton Diabetics;
    public TextField password;
    public TextField email;
    public TextField blood;
    public TextField emergency;
    public TextField phone;
    public ChoiceBox<String> gender;
    public Label errorReport;

    String[] list = {"Male","Female"};

    public void SaveNewPatient(ActionEvent event) throws SQLException, IOException {
        if(firstname.getText()=="" || lastname.getText()=="" || age.getText()=="" || String.valueOf(date.getValue())=="" || PID.getText()=="" || password.getText()=="" || email.getText()=="" ){
            errorReport.setText("Error");
        }else {
            if(checkIdEmail(PID.getText(),email.getText())==1){
                newPatient();
                errorReport.setText("Account Created Successfully");
            }else {
                errorReport.setText("Duplicate");
            }
        }
    }

    public void newPatient () throws SQLException, IOException {

        DbConnect connectDB = new DbConnect();
        Connection connection = connectDB.getConnect();

        String pid = PID.getText();
        String Fname = firstname.getText();
        String Lname = lastname.getText();
        int Age = Integer.parseInt(age.getText());
        String Date = String.valueOf(date.getValue());
        String Address = address.getText();

        String query = "INSERT INTO `patient`(`PID`, `FirstName`, `LastName`, `Age`, `DOB`, `Adress`, `ProfileImage`, `BloodGroup`, `Operation`, `Diabetes`, `Econtact`, `email`, `phone`, `gender`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        PreparedStatement ps = connection.prepareStatement(query);

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(save.getScene().getWindow());
        FileInputStream fileInputStream = new FileInputStream(file);

        ps.setBigDecimal(1, BigDecimal.valueOf(Long.parseLong(pid)));
        ps.setString(2,Fname);
        ps.setString(3,Lname);
        ps.setString(4, String.valueOf(Age));
        ps.setString(5,Date);
        ps.setString(6,Address);
        ps.setBinaryStream(7,fileInputStream,fileInputStream.available());
        ps.setString(8,blood.getText());
        ps.setBoolean(9,Operation.isSelected());
        ps.setBoolean(10,Diabetics.isSelected());
        ps.setString(11,emergency.getText());
        ps.setString(12,email.getText());
        ps.setString(13,phone.getText());
        ps.setString(14,gender.getValue());


        String queryuser = "INSERT INTO `user`(`PID`, `email`, `password`, `type`) VALUES (?, ?, ?, ?)";
        PreparedStatement ps1 = connection.prepareStatement(queryuser);
        ps1.setBigDecimal(1,BigDecimal.valueOf(Long.parseLong(pid)));
        ps1.setString(2, email.getText());
        ps1.setString(3, password.getText());
        ps1.setString(4,"user");

        ps1.execute();
        ps.execute();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender.getItems().addAll(list);
    }

    public void SignIn(ActionEvent event) throws IOException {
        openScreen(event,"LoginMain.fxml");
    }
}
