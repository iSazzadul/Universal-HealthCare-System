package com.aibrains.upms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewDoctorController extends Functions implements Initializable {

    public TextField fname;
    public TextField lname;
    public TextField cNo;
    public TextField degree;
    public TextField spec;
    public TextField email;
    public TextField password;
    public TextField did;
    public ChoiceBox<String> gender;
    public Button signup;
    public Label log;
    String[] list = {"Male","Female"};



    public void signup(ActionEvent event) throws SQLException, IOException {
        if(fname.getText()=="" || lname.getText()=="" || cNo.getText()=="" || degree.getText()==""
                || spec.getText()=="" || password.getText()=="" || email.getText()=="" || did.getText()=="" ){
            log.setText("Please Fill Everything");
        }else {
            if(email.getText().contains("@updms.com")==false) {
                log.setText("Email must be contain @updms.com");
            }else if(checkIdEmail(did.getText(),email.getText())==1){
                newDoctor();
                openScreen(event,"LoginMain.fxml");

            }else{
                log.setText("Email or NID already found");
            }
        }
    }


    public void newDoctor () throws SQLException, IOException {



        DbConnect connectDB = new DbConnect();
        Connection connection = connectDB.getConnect();

        String Did = did.getText();
        String Fname = fname.getText();
        String Lname = lname.getText();
        String Email = email.getText();
        String Degree = degree.getText();
        String Spec = spec.getText();
        String num = cNo.getText();

        String query = "INSERT INTO `doctor`(`DID`, `FirstName`, `LastName`, `Email`, `gender`, `Degree`, `Specialist`, `CellNumber`, `Image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        PreparedStatement ps = connection.prepareStatement(query);

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(signup.getScene().getWindow());
        FileInputStream fileInputStream = new FileInputStream(file);

        ps.setBigDecimal(1, BigDecimal.valueOf(Long.parseLong(Did)));
        ps.setString(2,Fname);
        ps.setString(3,Lname);
        ps.setString(4, Email);
        ps.setString(5,gender.getValue());
        ps.setString(6,Degree);
        ps.setString(7,Spec);
        ps.setString(8,num);
        ps.setBinaryStream(9,fileInputStream,fileInputStream.available());


        String queryuser = "INSERT INTO `user`(`PID`, `email`, `password`, `type`) VALUES (?, ?, ?, ?)";
        PreparedStatement ps1 = connection.prepareStatement(queryuser);
        ps1.setBigDecimal(1,BigDecimal.valueOf(Long.parseLong(Did)));
        ps1.setString(2, Email);
        ps1.setString(3, password.getText());
        ps1.setString(4,"Doctor");

        ps1.execute();
        ps.execute();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender.getItems().addAll(list);
    }

    public void back(ActionEvent event) throws IOException {
        openScreen(event,"LoginMain.fxml");
    }
}
