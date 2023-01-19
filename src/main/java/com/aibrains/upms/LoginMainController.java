package com.aibrains.upms;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginMainController extends Functions{

    public TextField loginID;
    public TextField password;
    public Label connres;
    public static int type;
    public static String uid;

    public void login(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("test.fxml"));

        DbConnect connectDB = new DbConnect();
        Connection connection = connectDB.getConnect();

        //String connectQueryLog = "SELECT * FROM `user` WHERE PID ='"+username.getText() +"'AND password = "+password.getText();
        //String connectQueryLog = "SELECT * FROM `user` WHERE PID ='20210104055'AND password = '123456'" ;

        String connectQueryLog = "SELECT * FROM `user` ";
        Statement statement = connection.createStatement();
        ResultSet queryOutput = statement.executeQuery(connectQueryLog);

        if (loginID.getText() != null || password.getText() != null) {
            while (queryOutput.next()) {
                String ID = queryOutput.getString("PID");
                String email = queryOutput.getString("email");
                String Pass = queryOutput.getString("password");
                String Type = queryOutput.getString("type");

                if (ID.equals(loginID.getText()) && Pass.equals(password.getText())) {
                    connres.setText("Success");
                    if(Type.equals("user")){
                        type = 1;
                        uid = loginID.getText();
                        openWindow(event,"pp.fxml");
                    }else if(Type.equals("Doctor")){
                        type = 2;
                        uid = loginID.getText();
                        openWindow(event,"DoctorDashboard.fxml");
                    }else if(Type.equals("Admin")) {
                        type = 3;
                        uid = loginID.getText();
                        openWindow(event, "AdminDashboard.fxml");
                    }
                }else {
                    connres.setText("Incorrect Username or Password !");
                }

            }
        }else {
            connres.setText("Please Input EveryThing");
        }
    }

    public void registet(ActionEvent event) throws IOException {
        openScreen(event,"NewPatient.fxml");
    }

    public void registeDoc(ActionEvent event) throws IOException {
        openScreen(event,"NewDoctor.fxml");
    }
}
