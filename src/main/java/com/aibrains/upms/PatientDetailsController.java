package com.aibrains.upms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class PatientDetailsController extends Functions implements Initializable {

    public TextField PID  ;
    public ListView<String> pList;
    public Label firstname;
    public Label lastname;
    public Label dob;
    public Label address;
    public Label name;
    public Button find;
    public TextField comment;


    public static String id ;
    public ImageView photo;
    public ListView<String> reportlist;
    public TextField reportinfo ;
    public Button upload;
    public static int reportsl ;
    public static String findID;
    public Button addButton;
    public Label blood;
    public Label email;
    public Label emergency;
    public Label number;
    public Label pid;
    public Label gender;
    public Label medilist;
    public TextField newmedicine;
    public Button updatemedilist;
    public Label allergymedi;
    public TextField newallergymedi;
    public Label error;


    @FXML
        public void find() {
            medilist.setText("");
            allergymedi.setText("");
            id = PID.getText();
            findID = id ;
            pList.getItems().clear();
            reportlist.getItems().clear();

            try {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("test.fxml"));

                DbConnect connectDB = new DbConnect();
                Connection connection = connectDB.getConnect();

                String connectQueryLog = "SELECT * FROM patient_log WHERE PID = " + id;
                Statement statement = connection.createStatement();
                ResultSet queryOutput = statement.executeQuery(connectQueryLog);
                while (queryOutput.next()) {
                    medilist.setText(queryOutput.getString("medlist"));
                    newmedicine.setText(queryOutput.getString("medlist"));
                    allergymedi.setText(queryOutput.getString("MediAllergy"));
                    newallergymedi.setText(queryOutput.getString("MediAllergy"));



                    String PID = queryOutput.getString("PID");
                    String Date = queryOutput.getString("Date");
                    String DoctorName = queryOutput.getString("DoctorName");
                    String Comment = queryOutput.getString("Comment");
                    String Reportinfo = queryOutput.getString("ReportInfo");
                    int SL = queryOutput.getInt("sl");
                    pList.getItems().add(Date + "\n" + DoctorName + "\n" + Comment + "\n");
                    reportlist.getItems().add((" " +SL + " -> "+Date + "\t" + Reportinfo));

                }


                String connectQueryPatient = "SELECT * FROM patient WHERE PID = " + id;
                statement = connection.createStatement();
                queryOutput = statement.executeQuery(connectQueryPatient);
                while ((queryOutput.next())) {
                    name.setText(queryOutput.getString("FirstName") + " " + queryOutput.getString("LastName"));
                    // lastname.setText(queryOutput.getString("LastName"));
                    address.setText(queryOutput.getString("Adress"));
                    dob.setText(queryOutput.getString("DOB"));
                    blood.setText(queryOutput.getString("BloodGroup"));
                    emergency.setText(queryOutput.getString("Econtact"));
                    email.setText(queryOutput.getString("email"));
                    number.setText(queryOutput.getString("phone"));
                    pid.setText(queryOutput.getString("PID"));
                    gender.setText(queryOutput.getString("gender"));
                }

                String connectQueryPhoto = "SELECT * FROM patient WHERE PID = " + id;
                statement = connection.createStatement();
                queryOutput = statement.executeQuery(connectQueryPhoto);
                if (queryOutput.next()) {
                    Blob blob = queryOutput.getBlob("ProfileImage");
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    photo.setImage(image);
                }


            } catch (SQLException e) {
               // throw new RuntimeException(e);
            }
    }

        public void add() throws SQLException, IOException {
        if(comment.getText()=="" || reportinfo.getText()==""){
             error.setText("Please Fill every Data");
        }else {
            addtoDB();
        }
    }

        public void addtoDB() throws SQLException, IOException {
            String Comment = comment.getText();
            String DocName = "Labib Rahman";
            String Date = String.valueOf(java.time.LocalDate.now());
            String ReportInfo = reportinfo.getText();
            int isCure = 0;


            String query = "INSERT INTO `patient_log` (`PID`, `Date`, `DoctorName`, `Comment`,`Report`, `ReportInfo`) VALUES ( ?,?, ?, ?, ?, ?);";

            DbConnect connectDB = new DbConnect();
            Connection connection = connectDB.getConnect();
            PreparedStatement ps = connection.prepareStatement(query);

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(addButton.getScene().getWindow());
            FileInputStream fileInputStream = new FileInputStream(file);

            ps.setBigDecimal(1, BigDecimal.valueOf(Long.parseLong(id)));
            ps.setString(2, Date);
            ps.setString(3, DoctorDashboardController.docname);
            ps.setString(4, Comment);
            ps.setString(6, ReportInfo);
            ps.setBinaryStream(5, fileInputStream, fileInputStream.available());
            ps.execute();
            find();
        }

    public void view(ActionEvent event) throws IOException {
        String rep =reportlist.getSelectionModel().getSelectedItems().toString();
        String arr[] = rep.split(" ");
        String sl = arr[1];
        reportsl = Integer.parseInt(sl);
        System.out.println(sl);
        openWindow(event,"LabTestReport.fxml");

    }

    public void UpdateMedi() throws SQLException, IOException {
        String Medilist = newmedicine.getText();
        String query = "UPDATE `patient_log` SET `medlist`='"+Medilist+"' WHERE PID="+id;

        DbConnect connectDB = new DbConnect();
        Connection connection = connectDB.getConnect();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.execute();
        find();
    }

    public void UpdateAllergyMedi() throws SQLException {
        String AllergyMedilist = newallergymedi.getText();
        String query = "UPDATE `patient_log` SET `MediAllergy`='"+AllergyMedilist+"' WHERE PID="+id;

        DbConnect connectDB = new DbConnect();
        Connection connection = connectDB.getConnect();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.execute();
        find();
    }

    public void addMedi() throws SQLException, IOException {
        if (newmedicine.getText() == "") {

        } else {
            UpdateMedi();
        }
    }
    public void addAllergyMedi() throws SQLException, IOException {
        if (newallergymedi.getText() == "") {

        } else {
            UpdateAllergyMedi();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(LoginMainController.type==1){
            addButton.setDisable(true);
        }
        id = LoginMainController.uid;
        PID.setText(id);
        find();
    }


}

