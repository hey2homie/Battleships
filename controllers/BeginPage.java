package controllers;

import controllers.classes.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BeginPage implements Initializable {

    @FXML
    Button qa;
    @FXML
    Button begin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void toSettings(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "../../stylefiles/settingsPage.fxml");
    }

    @FXML
    private void toQA(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "../../stylefiles/qa.fxml");
    }
}
