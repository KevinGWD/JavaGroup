package com.example.group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import static java.lang.Integer.parseInt;

public class HelloController {
    @FXML
    private TextField address;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField ownerID;
    @FXML
    private TextField carID;
    @FXML
    private TextField model;
    @FXML
    private TextField builtYear;
    @FXML
    private TextField cost;
    @FXML
    private TextArea description;
    @FXML
    private TextField email;
    @FXML
    private TextField make;
    @FXML
    private TextField carVIN;
    @FXML
    private DatePicker date;
    @FXML
    private Button addNew;
    @FXML
    private Button update;
    @FXML
    private Button findByOwner;
    @FXML
    private Button findByCarID;
    @FXML
    private Button findByDate;
    @FXML
    private TextField queryOwnerID;
    @FXML
    private TextField queryCarID;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView<String> displayArea;
    @FXML
    private ComboBox<String> type;

    public HelloController() {
    }


    public void  makeType(){  //making the ComboBox menu

        String[] types ={"Sedan", "SUV", "Van", "Truck"};
        type.getItems().removeAll();
        type.getItems().addAll(types);
        type.getSelectionModel().select("SUV");
    }

    public void initialize(){
        makeType();
    }

    public void addNewBtn(ActionEvent actionEvent) throws SQLException {
        try{DBUtil.insert(parseInt(ownerID.getText()), name.getText(),
                address.getText(), phone.getText(),
                email.getText(),parseInt(carID.getText()), make.getText(),
                model.getText(),parseInt(carVIN.getText()),parseInt(builtYear.getText()),
                type.getValue(), String.valueOf(date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                description.getText(), parseInt(cost.getText()));
            ownerID.setText("");
            name.setText("");
            address.setText("");
            phone.setText("");
            email.setText("");
            carID.setText("");
            make.setText("");
            model.setText("");
            carVIN.setText("");
            builtYear.setText("");
            makeType();
            date.setValue(null);
            description.setText("");
            cost.setText("");
        }
        catch(SQLException e){
            displayArea.setAccessibleText(e.getMessage());
        }

    }

    public void updateBtn(ActionEvent actionEvent) {

    }

    public void findByOwnerIDBtn(ActionEvent actionEvent) {

    }

    public void findByCarIDBtn(ActionEvent actionEvent) {

    }

    public void findByDateBtn(ActionEvent actionEvent) {

    }

    public void selectType(ActionEvent actionEvent) {

    }
}