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
    private TableColumn<Repair,Integer> repairIDColumn;
    @FXML
    private TableColumn<Repair,Integer> ownerIDColumn;
    @FXML
    private TableColumn<Repair,Integer> carIDColumn;
    @FXML
    private TableColumn<Repair, Date> repairDateColumn;
    @FXML
    private TableColumn<Repair,String> repairDescriptionColumn;
    @FXML
    private TableColumn<Repair,Integer> costColumn;
    @FXML
    private Label message;
    @FXML
    private Button addNew;


    public HelloController() {
    }


    public void  makeType(){  //making the ComboBox menu

        String[] types ={"Sedan", "SUV", "Van", "Truck"};
        type.getItems().removeAll();
        type.getItems().addAll(types);
        type.getSelectionModel().select("SUV");
    }



    public void addNewBtn(ActionEvent actionEvent) throws SQLException {
        try{
            addNew.setDisable(true);
            DBUtil.insert(parseInt(ownerID.getText()), name.getText(),
                address.getText(), phone.getText(),
                email.getText(),parseInt(carID.getText()), make.getText(),
                model.getText(),parseInt(carVIN.getText()),parseInt(builtYear.getText()),
                type.getValue(), String.valueOf(date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                description.getText(), parseInt(cost.getText()));
            addNew.setDisable(false);
            message.setText("");}
        catch(Exception e){
            message.setText("Please do not leave input empty and use correct data type");
            addNew.setDisable(false);
            return;
        }
            populateData();
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
//            makeType();
            date.setValue(null);
            description.setText("");
            cost.setText("");
    }

    public void updateBtn(ActionEvent actionEvent) {
        Repair repair=(Repair) displayArea.getSelectionModel().getSelectedItem(); //get repair obj to update
    }

    public void findByOwnerIDBtn(ActionEvent actionEvent) throws SQLException {
        String sql="SELECT * FROM REPAIR WHERE OWNERID="+queryOwnerID.getText();
        fillingTable(sql);
    }

    public void findByCarIDBtn(ActionEvent actionEvent) throws SQLException {
        String sql="SELECT * FROM REPAIR WHERE CARID="+queryCarID.getText();
        fillingTable(sql);
    }

    public void findByDateBtn(ActionEvent actionEvent) throws SQLException {
        String sql="SELECT * FROM REPAIR WHERE S_DATE BETWEEN '"+startDate.getValue()+"' AND '"+endDate.getValue()+"'";
        fillingTable(sql);
    }

    public void deleteBtn(ActionEvent actionEvent) throws SQLException {
        Repair repair=(Repair) displayArea.getSelectionModel().getSelectedItem();
        DBUtil.deleteData("REPAIR", "repairID",repair.getRepairID());
        DBUtil.deleteData("OWNER", "OWNERID",repair.getOwnerID());
        DBUtil.deleteData("CAR", "CARID",repair.getCarID());
        populateData();
    }
    public void populateData () throws SQLException {
           String sql="SELECT * FROM REPAIR";
           fillingTable(sql);
        }

    public void fillingTable(String sql) throws SQLException {
        ResultSet rs=DBUtil.query(sql);
        ObservableList<Repair> repairs= FXCollections.observableArrayList();
        while (rs.next()){
            Repair repair=new Repair(rs.getInt("REPAIRID"), rs.getInt("OWNERID"),
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

    public void initialize() throws SQLException{
        makeType();
        populateData();
    }

    public void saveUpdate(ActionEvent actionEvent) {


    }
}