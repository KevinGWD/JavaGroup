package com.example.group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void addNewBtn(ActionEvent actionEvent) {

    }

    public void updateBtn(ActionEvent actionEvent) {

    }

    public void findByOwnerIDBtn(ActionEvent actionEvent) {

    }

    public void findByCarIDBtn(ActionEvent actionEvent) {

    }

    public void findByDateBtn(ActionEvent actionEvent) {

    }
}