package com.example.group_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.text.SimpleDateFormat;
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
    private TextField queryOwnerID;
    @FXML
    private TextField queryCarID;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView<Repair> displayArea;
    @FXML
    private ComboBox<String> type;
    @FXML
    private TableColumn<Repair, Integer> repairIDColumn;
    @FXML
    private TableColumn<Repair, Integer> ownerIDColumn;
    @FXML
    private TableColumn<Repair, Integer> carIDColumn;
    @FXML
    private TableColumn<Repair, Date> repairDateColumn;
    @FXML
    private TableColumn<Repair, String> repairDescriptionColumn;
    @FXML
    private TableColumn<Repair, Integer> costColumn;
    @FXML
    private Label message;
    @FXML
    private Button addNewOwner;
    @FXML
    private Button loadInformation;
    @FXML
    private Button update;
    @FXML
    private Button delete;


    public void makeType() {  //making the ComboBox menu

        String[] types = {"Sedan", "SUV", "Van", "Truck"};
        type.getItems().removeAll();
        type.getItems().addAll(types);
        type.getSelectionModel().select("SUV");
    }
    
    public void addNewJobBtn(ActionEvent actionEvent) throws SQLException {
        try {
            System.out.println("clicked add job");
            DBUtil.insertJob(parseInt(ownerID.getText()),  parseInt(carID.getText()),
                    String.valueOf(date.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yy"))),
                    description.getText(), parseInt(cost.getText()));
            delete.setDisable(true);
            message.setText("");
        } catch (Exception e) {
            message.setText("Please do not leave input empty and use correct data type");
            delete.setDisable(true);
            return;
        }
        message.setText("");
        populateData();
        resetInput();
        update.setDisable(true);
        delete.setDisable(true);
    }    
    public void addNewCarBtn(ActionEvent actionEvent) throws SQLException {
        try {
            System.out.println("clicked  car");
            DBUtil.insertCar(parseInt(carID.getText()), make.getText(),
                    model.getText(), parseInt(carVIN.getText()), parseInt(builtYear.getText()),
                    type.getValue());
            delete.setDisable(true);
            message.setText("");
        } catch (Exception e) {
            message.setText("Please do not leave input empty and use correct data type");
            delete.setDisable(true);
            return;
        }
        delete.setDisable(true);
    }

    public void addNewOwnerBtn(ActionEvent actionEvent) throws SQLException {
        try {
            System.out.println("clicked owner ");

            DBUtil.insertOwner(parseInt(ownerID.getText()), name.getText(),
                    address.getText(), phone.getText(),
                    email.getText());
            message.setText("");
        } catch (Exception e) {
            message.setText("Please do not leave input empty and use correct data type");
            return;
        }
        delete.setDisable(true);
    }

    public void loadInformationBtn(ActionEvent actionEvent) throws SQLException {
        Repair repair = (Repair) displayArea.getSelectionModel().getSelectedItem(); //get repair obj to update
        String sqlGetRepair = "SELECT * FROM REPAIR WHERE REPAIRID=" + repair.getRepairID();
        ResultSet rsRepair = DBUtil.query(sqlGetRepair);
        String sqlGetOwner = "SELECT * FROM OWNER WHERE OWNERID=" + repair.getOwnerID();
        ResultSet rsOwer = DBUtil.query(sqlGetOwner);
        String sqlGetCar = "SELECT * FROM CAR WHERE CARID=" + repair.getCarID();
        ResultSet rsCar = DBUtil.query(sqlGetCar);
        while (rsRepair.next()) {
            cost.setText(rsRepair.getString("S_COST"));
            description.setText(rsRepair.getString("S_DESCRIPTION"));
        }
        while (rsOwer.next()) {
            name.setText(rsOwer.getString("NAME"));
            address.setText(rsOwer.getString("ADDRESS"));
            phone.setText(rsOwer.getString("PHONE"));
            email.setText(rsOwer.getString("EMAIL"));
            ownerID.setText(rsOwer.getString("OWNERID"));
        }
        while (rsCar.next()) {
            carID.setText(rsCar.getString("CARID"));
            make.setText(rsCar.getString("MAKE"));
            model.setText(rsCar.getString("MODEL"));
            carVIN.setText(rsCar.getString("VIN"));
            builtYear.setText(rsCar.getString("BUILDYEAR"));
            type.setValue(rsCar.getString("TYPE"));
        }
        date.setValue(LocalDate.now());

    }

    public void findByOwnerIDBtn(ActionEvent actionEvent) throws SQLException {
        String sql = "SELECT * FROM REPAIR WHERE OWNERID=" + queryOwnerID.getText();
        fillingTable(sql);
    }

    public void findByCarIDBtn(ActionEvent actionEvent) throws SQLException {
        String sql = "SELECT * FROM REPAIR WHERE CARID=" + queryCarID.getText();
        fillingTable(sql);
    }

    public void findByDateBtn(ActionEvent actionEvent) throws SQLException {
        String sql = "SELECT * FROM REPAIR WHERE S_DATE BETWEEN '" + String.valueOf(startDate.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yy"))) + "' AND '"
                + String.valueOf(endDate.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yy"))) + "'";
        fillingTable(sql);
    }

    public void deleteBtn(ActionEvent actionEvent) throws SQLException {
        Repair repair = (Repair) displayArea.getSelectionModel().getSelectedItem();
        DBUtil.deleteData("REPAIR", "repairID", repair.getRepairID());
//        DBUtil.deleteData("OWNER", "OWNERID", repair.getOwnerID());
//        DBUtil.deleteData("CAR", "CARID", repair.getCarID());
        populateData();
        resetInput();
        delete.setDisable(true);
        update.setDisable(true);
        update.setDisable(true);
    }

    public void populateData() throws SQLException {
        String sql = "SELECT * FROM REPAIR";
        fillingTable(sql);
    }

    public void fillingTable(String sql) throws SQLException {
        ResultSet rs = DBUtil.query(sql);
        ObservableList<Repair> repairs = FXCollections.observableArrayList();
        while (rs.next()) {
            Repair repair = new Repair(rs.getInt("REPAIRID"), rs.getInt("OWNERID"),
                    rs.getInt("CARID"), rs.getDate("S_DATE"),
                    rs.getString("S_DESCRIPTION"), rs.getInt("S_COST"));
            repairs.add(repair);
        }
        System.out.println("after while");
        repairIDColumn.setCellValueFactory(new PropertyValueFactory<>("repairID"));
        ownerIDColumn.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        carIDColumn.setCellValueFactory(new PropertyValueFactory<>("carID"));
        repairDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        repairDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        displayArea.getItems().clear();
        displayArea.getItems().addAll(repairs);
        repairDateColumn.setSortType(TableColumn.SortType.ASCENDING);
        displayArea.getSortOrder().add(repairDateColumn);
        displayArea.sort();
    }

    public void initialize() throws SQLException {
        makeType();
        populateData();
        update.setDisable(true);
        delete.setDisable(true);

        displayArea.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                update.setDisable(false);
                delete.setDisable(false);
            }
        });
    }

    public void updateBtn(ActionEvent actionEvent) throws SQLException {
        DBUtil.update(parseInt(ownerID.getText()), name.getText(),
                address.getText(), phone.getText(),
                email.getText(), parseInt(carID.getText()), make.getText(),
                model.getText(), parseInt(carVIN.getText()), parseInt(builtYear.getText()),
                type.getValue(), date.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yy")),
                description.getText(), parseInt(cost.getText()));
        populateData();
        resetInput();
        ownerID.setDisable(false);
        carID.setDisable(false);
    }

    public void resetInput() {
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
        date.setValue(null);
        description.setText("");
        cost.setText("");
    }

}