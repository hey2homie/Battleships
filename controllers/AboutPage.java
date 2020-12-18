package controllers;

import controllers.classes.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutPage implements Initializable {

    @FXML
    Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "../../stylefiles/begin.fxml");
    }

}
